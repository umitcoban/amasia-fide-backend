package com.umityasincoban.amasia_fide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password
) {
}
