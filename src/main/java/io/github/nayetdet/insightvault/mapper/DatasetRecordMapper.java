package io.github.nayetdet.insightvault.mapper;

import io.github.nayetdet.insightvault.model.DatasetRecord;
import io.github.nayetdet.insightvault.payload.dto.DatasetRecordDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedDatasetRecordDTO;
import io.github.nayetdet.insightvault.repository.DatasetRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DatasetRecordMapper {

    private final DatasetRecordRepository datasetRecordRepository;
    private final DatasetMapper datasetMapper;

    public DatasetRecordDTO toDTO(DatasetRecord datasetRecord) {
        return DatasetRecordDTO
                .builder()
                .id(datasetRecord.getId())
                .data(datasetRecord.getData())
                .dataset(datasetMapper.toDTO(datasetRecord.getDatasetId()))
                .createdAt(datasetRecord.getCreatedAt())
                .updatedAt(datasetRecord.getUpdatedAt())
                .build();
    }

    public SimplifiedDatasetRecordDTO toSimplifiedDTO(DatasetRecord datasetRecord) {
        return SimplifiedDatasetRecordDTO
                .builder()
                .id(datasetRecord.getId())
                .dataset(datasetMapper.toDTO(datasetRecord.getDatasetId()))
                .createdAt(datasetRecord.getCreatedAt())
                .updatedAt(datasetRecord.getUpdatedAt())
                .build();
    }

    public SimplifiedDatasetRecordDTO toSimplifiedDTO(String id) {
        return datasetRecordRepository
                .findById(id)
                .map(this::toSimplifiedDTO)
                .orElse(null);
    }

}
