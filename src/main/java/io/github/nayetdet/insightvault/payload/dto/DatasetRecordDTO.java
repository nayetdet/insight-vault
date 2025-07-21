package io.github.nayetdet.insightvault.payload.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasetRecordDTO {

    private String id;
    private List<Map<String, Object>> data;
    private DatasetDTO dataset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
