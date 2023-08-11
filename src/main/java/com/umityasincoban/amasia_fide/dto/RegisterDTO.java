package com.umityasincoban.amasia_fide.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Information required for the user to register")
public record RegisterDTO(
        @JsonProperty(required = true)
        @NotBlank(message = "firstname should be not empty or null")
        @Schema(example = "Alex", description = "user first name")
        String firstName,
        @Schema(example = "john", description = "user middle name")
        String middleName,
        @JsonProperty(required = true)
        @NotBlank(message = "lastName should be not empty or null")
        @Schema(example = "Doe", description = "user last name")
        String lastName,
        @JsonProperty(required = true)
        @NotBlank(message = "firstname should be not empty or null")
        @Email(message = "Please entered correct email addresses")
        @Schema(example = "test@example.com", description = "user email addresses and username")
        String email,
        @JsonProperty(required = true)
        @Size(min = 6, message = "Password should be min 6 characters")
        @Schema(example = "12345678", description = "user email addresses and username", minimum = "6")
        String password,
        @JsonProperty(required = true)
        @Size(min = 11, max = 11, message = "citizenNumber should be max 11 and min 11 character")
        @Schema(example = "11111111111", description = "user citizen number", minimum = "11", maximum = "11")
        String citizenNumber,
        @JsonProperty(required = true)
        @NotBlank(message = "phone should be not empty or null")
        @Size(min = 10, max = 10)
        @Schema(example = "5051111111", description = "user phone number", minimum = "10", maximum = "10")
        String phone
) {
}
