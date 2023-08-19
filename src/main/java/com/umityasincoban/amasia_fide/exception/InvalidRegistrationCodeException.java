package com.umityasincoban.amasia_fide.exception;

public class InvalidRegistrationCodeException extends RuntimeException{
    public InvalidRegistrationCodeException() {
    }

    public InvalidRegistrationCodeException(String message) {
        super(message);
    }
}
