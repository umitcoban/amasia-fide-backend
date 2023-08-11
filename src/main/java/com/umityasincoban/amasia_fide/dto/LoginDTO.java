package com.umityasincoban.amasia_fide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Information required for the user to log in")
public record LoginDTO(
        @Schema(description = "user email address", example = "test@example.com")
        @NotBlank String username,
        @Schema(description = "user password", example = "12345678")
        @NotBlank @Size(min = 6) String password
) {
}
