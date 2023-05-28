package com.umityasincoban.amasia_fide.dto;

public record ExceptionDTO (
        int status,
        String message,
        long timeStamp
)
{ }
