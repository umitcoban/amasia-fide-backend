package com.umityasincoban.amasia_fide.dto;


public record TokenDTO(
        String token,
        long timeStamp,
        int status
) {
}
