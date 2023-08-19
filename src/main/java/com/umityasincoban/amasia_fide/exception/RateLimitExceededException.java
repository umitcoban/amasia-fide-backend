package com.umityasincoban.amasia_fide.exception;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException() {
    }

    public RateLimitExceededException(String message) {
        super(message);
    }
}
