package io.github.nayetdet.insightvault.service;

import io.github.nayetdet.insightvault.exception.RecordNotFoundException;
import io.github.nayetdet.insightvault.mapper.DatasetRecordMapper;
import io.github.nayetdet.insightvault.model.DatasetRecord;
import io.github.nayetdet.insightvault.payload.dto.DatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedDatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.query.DatasetRecordQuery;
import io.github.nayetdet.insightvault.payload.query.page.ApplicationPage;
import io.github.nayetdet.insightvault.repository.DatasetRecordRepository;
import io.github.nayetdet.insightvault.utils.json.JsonParsingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.Tuple;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DatasetRecordService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final DatasetRecordRepository datasetRecordRepository;
    private final DatasetRecordMapper datasetRecordMapper;

    public ApplicationPage<SimplifiedDatasetRecordDTO> search(DatasetRecordQuery query) {
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize());
        NativeQuery nativeQuery = NativeQuery
                .builder()
                .withQuery(query.getQuery())
                .withPageable(pageable)
                .withSort(query.getSort())
                .build();

        List<SimplifiedDatasetRecordDTO> content = elasticsearchOperations
                .search(nativeQuery, DatasetRecord.class)
                .get()
                .map(hit -> datasetRecordMapper.toSimplifiedDTO(hit.getContent()))
                .toList();

        return new ApplicationPage<>(content, pageable);
    }

    public Optional<DatasetRecordDTO> find(String id) {
        return datasetRecordRepository.findById(id).map(datasetRecordMapper::toDTO);
    }

    public void create(String id, Long datasetId, MultipartFile file) {
        LocalDateTime now = LocalDateTime.now();
        Tuple<String, List<Map<String, Object>>> parsedFile = JsonParsingStrategy.parse(file);
        DatasetRecord datasetRecord = DatasetRecord
                .builder()
                .id(id)
                .datasetId(datasetId)
                .dataText(parsedFile._1())
                .data(parsedFile._2())
                .createdAt(now)
                .updatedAt(now)
                .build();

        datasetRecordRepository.save(datasetRecord);
    }

    public void delete(String id) {
        DatasetRecord datasetRecord = datasetRecordRepository
                .findById(id)
                .orElseThrow(RecordNotFoundException::new);

        datasetRecordRepository.delete(datasetRecord);
    }

}
