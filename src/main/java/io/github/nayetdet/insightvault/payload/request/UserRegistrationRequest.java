package io.github.nayetdet.insightvault.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^(?!_+$)[a-z0-9_]+$", message = "Username should only contain letters and numbers")
    @Schema(example = "string")
    private String username;

    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    @Size(max = 255)
    @Schema(example = "string@gmail.com")
    private String email;

    @NotBlank
    @Size(max = 255)
    private String password;

}
