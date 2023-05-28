package com.umityasincoban.amasia_fide.exception;

import com.umityasincoban.amasia_fide.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler
    public static ResponseEntity<ExceptionDTO> globalExceptionHandler(Exception e){
        logger.warning(e.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(400, e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public static ResponseEntity<ExceptionDTO> badCredentialException(BadCredentialsException badCredentialsException){
        logger.warning(badCredentialsException.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(400, "Please must be entered valid user information",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

}
