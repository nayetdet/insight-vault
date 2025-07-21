package io.github.nayetdet.insightvault.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRefreshTokenRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String refreshToken;

}
