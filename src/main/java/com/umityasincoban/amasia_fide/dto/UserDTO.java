package com.umityasincoban.amasia_fide.dto;

import java.time.ZonedDateTime;

public record UserDTO(
        long id,
        String firstName,
        String middleName,
        String lastName,
        String citizenNumber,
        String email,
        String phone,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
)
{
}
