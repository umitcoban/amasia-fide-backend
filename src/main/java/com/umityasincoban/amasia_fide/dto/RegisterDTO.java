package com.umityasincoban.amasia_fide.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterDTO(
        @NotBlank String firstName,
        String middleName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @Size(min = 6) String password,
        @Size(min = 11, max = 11) String citizenNumber,
        String phone
) {
}
