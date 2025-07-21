package io.github.nayetdet.insightvault.payload.dto;

import io.github.nayetdet.insightvault.payload.dto.simplified.SimplifiedDatasetRecordDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightDTO {

    private Long id;
    private String question;
    private String answer;
    private UserDTO user;
    private SimplifiedDatasetRecordDTO datasetRecord;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
