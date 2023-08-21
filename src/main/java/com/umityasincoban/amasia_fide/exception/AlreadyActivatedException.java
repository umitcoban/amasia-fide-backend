package com.umityasincoban.amasia_fide.exception;

public class AlreadyActivatedException extends RuntimeException{

    public AlreadyActivatedException() {
    }

    public AlreadyActivatedException(String message) {
        super(message);
    }
}
