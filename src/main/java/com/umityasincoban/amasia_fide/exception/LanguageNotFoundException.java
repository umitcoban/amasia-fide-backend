package com.umityasincoban.amasia_fide.exception;

public class LanguageNotFoundException extends RuntimeException{
    public LanguageNotFoundException() {
        super();
    }
    public LanguageNotFoundException(String message) {
        super(message);
    }
}
