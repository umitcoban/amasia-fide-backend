package com.umityasincoban.amasia_fide.dto;

public record ExceptionDTO<T> (
        int status,
        T message,
        long timeStamp
)
{ }
