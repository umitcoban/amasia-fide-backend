package com.umityasincoban.amasia_fide.dto;

import jakarta.validation.constraints.NotBlank;

public record CityDTO(
        int id,
        @NotBlank String name
) {
}
