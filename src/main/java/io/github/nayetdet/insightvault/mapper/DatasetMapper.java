package io.github.nayetdet.insightvault.mapper;

import io.github.nayetdet.insightvault.model.Dataset;
import io.github.nayetdet.insightvault.payload.dto.DatasetDTO;
import io.github.nayetdet.insightvault.repository.DatasetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class DatasetMapper {

    @Value("${minio.outer-endpoint}")
    private String outerEndpoint;

    @Value("${minio.bucket}")
    private String bucket;

    private final UserMapper userMapper;
    private final DatasetRepository datasetRepository;

    public DatasetDTO toDTO(Dataset dataset) {
        String url = UriComponentsBuilder
                .fromUriString(outerEndpoint)
                .pathSegment(bucket)
                .pathSegment(dataset.getName())
                .toUriString();

        return DatasetDTO
                .builder()
                .id(dataset.getId())
                .recordId(dataset.getRecordId())
                .name(dataset.getName())
                .url(url)
                .contentType(dataset.getContentType())
                .sizeInBytes(dataset.getSizeInBytes())
                .user(userMapper.toDTO(dataset.getUser()))
                .createdAt(dataset.getCreatedAt())
                .updatedAt(dataset.getUpdatedAt())
                .build();
    }

    public DatasetDTO toDTO(Long id) {
        return datasetRepository
                .findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

}
