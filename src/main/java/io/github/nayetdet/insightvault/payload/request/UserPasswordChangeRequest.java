package io.github.nayetdet.insightvault.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordChangeRequest {

    @NotBlank
    @Size(max = 255)
    private String password;

}
