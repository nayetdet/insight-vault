package io.github.nayetdet.insightvault.mapper;

import io.github.nayetdet.insightvault.model.Insight;
import io.github.nayetdet.insightvault.payload.dto.InsightDTO;
import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedInsightDTO;
import io.github.nayetdet.insightvault.payload.request.InsightRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InsightMapper {

    private final UserMapper userMapper;
    private final DatasetRecordMapper datasetRecordMapper;

    public Insight toModel(InsightRequest request, String answer) {
        return Insight
                .builder()
                .recordId(request.getRecordId())
                .question(request.getQuestion())
                .answer(answer)
                .user(userMapper.toModel(request.getUsername()))
                .build();
    }

    public InsightDTO toDTO(Insight insight) {
        return InsightDTO
                .builder()
                .id(insight.getId())
                .question(insight.getQuestion())
                .answer(insight.getAnswer())
                .user(userMapper.toDTO(insight.getUser()))
                .datasetRecord(datasetRecordMapper.toSimplifiedDTO(insight.getRecordId()))
                .createdAt(insight.getCreatedAt())
                .updatedAt(insight.getUpdatedAt())
                .build();
    }

    public SimplifiedInsightDTO toSimplifiedDTO(Insight insight) {
        return SimplifiedInsightDTO
                .builder()
                .id(insight.getId())
                .question(insight.getQuestion())
                .user(userMapper.toDTO(insight.getUser()))
                .datasetRecord(datasetRecordMapper.toSimplifiedDTO(insight.getRecordId()))
                .createdAt(insight.getCreatedAt())
                .updatedAt(insight.getUpdatedAt())
                .build();
    }

}
