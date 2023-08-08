package com.umityasincoban.amasia_fide.exception;

import com.umityasincoban.amasia_fide.dto.ExceptionDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler
    public static ResponseEntity<ExceptionDTO> globalExceptionHandler(Exception e){
        logger.warning(e.getMessage());
        logger.warning(Arrays.toString(e.getStackTrace()));
        var uuid = UUID.randomUUID().toString();
        logger.warning("uuid: " + uuid);
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), String.format("Oops! Something went wrong {%s}", uuid), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public static ResponseEntity<ExceptionDTO> validationExceptionHandler(MethodArgumentNotValidException e){
        logger.warning(e.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Validation Failed", System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public static ResponseEntity<ExceptionDTO> badCredentialException(UserAlreadyExistException badCredentialsException){
        logger.warning(badCredentialsException.getMessage());
        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), "Please must be entered valid user information",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public static ResponseEntity<ExceptionDTO> userNotFoundException(UserNotFoundException userNotFoundException){
        logger.warning(userNotFoundException.getMessage());

        return new ResponseEntity<>(new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), userNotFoundException.getMessage(),
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

}
