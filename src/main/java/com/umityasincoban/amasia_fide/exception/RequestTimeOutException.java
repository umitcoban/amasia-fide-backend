package com.umityasincoban.amasia_fide.exception;

public class RequestTimeOutException extends  RuntimeException{
    public RequestTimeOutException() {
    }

    public RequestTimeOutException(String message) {
        super(message);
    }
}
