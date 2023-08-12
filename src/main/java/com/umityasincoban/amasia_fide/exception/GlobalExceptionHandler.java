package com.umityasincoban.amasia_fide.exception;

import com.umityasincoban.amasia_fide.dto.ExceptionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<ExceptionDTO<String>> globalExceptionHandler(Exception e, HttpServletRequest request){
        logger.warning(e.getMessage());
        logger.warning(Arrays.toString(e.getStackTrace()));
        var uuid = UUID.randomUUID().toString();
        logger.warning("uuid: " + uuid);
        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), String.format("Oops! Something went wrong {%s}", uuid), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO<List<String>>> validationExceptionHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> errorMessages = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ExceptionDTO<List<String>> response = new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), errorMessages, System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public static ResponseEntity<ExceptionDTO<String>> badCredentialException(UserAlreadyExistException userAlreadyExistException, HttpServletRequest request){
        logger.warning(userAlreadyExistException.getMessage());
        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "userAlreadyExistMessage",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public static ResponseEntity<ExceptionDTO<String>> userNotFoundException(UserNotFoundException userNotFoundException, HttpServletRequest request){
        logger.warning(userNotFoundException.getMessage());

        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Lütfen bilgilerinizin doğrulundan emin olun",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

}
