package io.github.nayetdet.insightvault.payload.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private String username;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime accessExpiresAt;
    private LocalDateTime refreshExpiresAt;

}
