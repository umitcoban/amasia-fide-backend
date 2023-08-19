package com.umityasincoban.amasia_fide.exception;

public class EmailTemplateNotFoundException extends RuntimeException{
    public EmailTemplateNotFoundException() {
    }

    public EmailTemplateNotFoundException(String message) {
        super(message);
    }
}
