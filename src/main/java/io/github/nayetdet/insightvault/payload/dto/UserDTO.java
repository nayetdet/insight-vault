package io.github.nayetdet.insightvault.payload.dto;

import io.github.nayetdet.insightvault.model.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
