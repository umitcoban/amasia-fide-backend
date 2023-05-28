package com.umityasincoban.amasia_fide.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
        int id,
        @NotBlank String name,
        String description
) {
}
