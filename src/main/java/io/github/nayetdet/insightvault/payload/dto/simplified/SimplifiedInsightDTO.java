package io.github.nayetdet.insightvault.payload.dto.simplified;

import io.github.nayetdet.insightvault.payload.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedInsightDTO {

    private Long id;
    private String question;
    private UserDTO user;
    private SimplifiedDatasetRecordDTO datasetRecord;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
