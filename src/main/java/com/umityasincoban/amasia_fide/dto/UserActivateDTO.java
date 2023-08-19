package com.umityasincoban.amasia_fide.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserActivateDTO(
        @Email
        @NotNull
        String email,
        @NotNull
        int activationCode
) {
}
