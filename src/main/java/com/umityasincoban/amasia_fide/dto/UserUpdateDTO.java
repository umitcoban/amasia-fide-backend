package com.umityasincoban.amasia_fide.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        @JsonProperty(required = true)
        @NotNull
        long id,
        String firstName,
        String middleName,
        String lastName,
        String citizenNumber,
        String password,
        String phone
) {
}
