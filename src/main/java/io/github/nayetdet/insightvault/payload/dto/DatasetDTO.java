package io.github.nayetdet.insightvault.payload.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasetDTO {

    private Long id;
    private String recordId;
    private String name;
    private String url;
    private String contentType;
    private Long sizeInBytes;
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
