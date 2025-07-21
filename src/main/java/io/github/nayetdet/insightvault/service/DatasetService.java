package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.exception.DatasetModificationForbiddenException;
import io.github.nayetdet.insightvault.exception.DatasetNotFoundException;
import io.github.nayetdet.insightvault.exception.UserNotFoundException;
import io.github.nayetdet.insightvault.mapper.DatasetMapper;
import io.github.nayetdet.insightvault.model.Dataset;
import io.github.nayetdet.insightvault.model.User;
import io.github.nayetdet.insightvault.payload.dto.DatasetDTO;
import io.github.nayetdet.insightvault.payload.query.DatasetQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.payload.request.DatasetRequest;
import io.github.nayetdet.insightvault.repository.DatasetRepository;
import io.github.nayetdet.insightvault.repository.UserRepository;
import io.github.nayetdet.insightvault.security.provider.UserContextProvider;
import io.github.nayetdet.insightvault.service.validator.DatasetValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DatasetService {

    private final UserRepository userRepository;

    private final RagService ragService;
    private final MinioService minioService;
    private final DatasetRecordService datasetRecordService;

    private final DatasetRepository datasetRepository;
    private final DatasetMapper datasetMapper;
    private final DatasetValidator datasetValidator;

    @Transactional(readOnly = true)
    public ApplicationPage<DatasetDTO> search(DatasetQuery query) {
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), query.getSort());
        Page<Dataset> page = datasetRepository.findAll(query.getSpecification(), pageable);
        return new ApplicationPage<>(page.map(datasetMapper::toDTO));
    }

    @Transactional(readOnly = true)
    public Optional<DatasetDTO> find(Long id) {
        return datasetRepository.findById(id).map(datasetMapper::toDTO);
    }

    @Transactional
    public DatasetDTO create(DatasetRequest request, MultipartFile file) {
        UserContextProvider.assertAuthorizedContext(request.getUsername(), DatasetModificationForbiddenException.class);
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);

        Dataset dataset = Dataset
                .builder()
                .recordId(UUID.randomUUID().toString())
                .name(minioService.create(file))
                .contentType(file.getContentType())
                .sizeInBytes(file.getSize())
                .user(user)
                .build();

        datasetValidator.validate(dataset);
        dataset = datasetRepository.save(dataset);

        ragService.ingest(file);
        datasetRecordService.create(dataset.getRecordId(), dataset.getId(), file);
        return datasetMapper.toDTO(dataset);
    }

    @Transactional
    public void delete(Long id) {
        Dataset dataset = datasetRepository
                .findById(id)
                .orElseThrow(DatasetNotFoundException::new);

        UserContextProvider.assertAuthorizedContext(dataset.getUser().getUsername(), DatasetModificationForbiddenException.class);
        minioService.delete(dataset.getName());
        datasetRepository.delete(dataset);
        datasetRecordService.delete(dataset.getRecordId());
    }

}
