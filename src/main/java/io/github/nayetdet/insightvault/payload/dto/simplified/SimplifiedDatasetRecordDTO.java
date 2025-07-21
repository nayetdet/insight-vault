package io.github.nayetdet.insightvault.payload.dto.simplified;

import io.github.nayetdet.insightvault.payload.dto.DatasetDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimplifiedDatasetRecordDTO {

    private String id;
    private DatasetDTO dataset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
