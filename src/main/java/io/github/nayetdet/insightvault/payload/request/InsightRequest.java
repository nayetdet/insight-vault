package io.github.nayetdet.insightvault.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsightRequest {

    @NotBlank
    private String recordId;

    @NotBlank
    @Size(max = 500)
    private String question;

    @NotBlank
    private String username;

}
