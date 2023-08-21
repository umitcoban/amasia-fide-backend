package com.umityasincoban.amasia_fide.exception;

import com.umityasincoban.amasia_fide.dto.ExceptionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.AccountLockedException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public static ResponseEntity<ExceptionDTO<String>> globalExceptionHandler(Exception e, HttpServletRequest request){
        logger.warning(e.getClass().getName());
        logger.warning(e.getMessage());
        logger.warning(Arrays.asList(e.getStackTrace()).toString());
        var uuid = UUID.randomUUID().toString();
        logger.warning("uuid: " + uuid);
        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), String.format("Hay Aksi! Bir şeylerde hata oldu {%s}",
                uuid), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public static ResponseEntity<ExceptionDTO<String>> badCredentialExceptionHandler(BadCredentialsException e, HttpServletRequest request){
        logger.warning(e.getMessage());
        logger.warning(Arrays.asList(e.getStackTrace()).toString());
        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Lütfen güvenlik bilgierinizi kontrol ediniz.", System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountLockedException.class)
    public static void accountLockedExceptionHandler(AccountLockedException e, HttpServletRequest request){
        logger.warning(e.getClass().getName());
        logger.warning(e.getMessage());
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
        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Kullanıcı zaten mevcut. Lütfen bilgilerinizi kontrol ediniz.",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public static ResponseEntity<ExceptionDTO<String>> userNotFoundException(UserNotFoundException userNotFoundException, HttpServletRequest request){
        logger.warning(userNotFoundException.getMessage());
        logger.warning(Arrays.asList(userNotFoundException.getStackTrace()).toString());
        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Lütfen bilgilerinizi eksiksiz girdiğinizden emin olunuz.",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRegistrationCodeException.class)
    public static ResponseEntity<ExceptionDTO<String>> invalidRegistrationCodeException(InvalidRegistrationCodeException invalidRegistrationCodeException, HttpServletRequest request){
        logger.warning(invalidRegistrationCodeException.getMessage());

        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Girmiş olduğunuz kod geçersiz.",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RequestTimeOutException.class)
    public static ResponseEntity<ExceptionDTO<String>> requestTimeOutException(RequestTimeOutException requestTimeOutException, HttpServletRequest request){
        logger.warning(requestTimeOutException.getMessage());

        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "İstediğiniz zaman aşımına uğradı lütfen daha sonra tekrar deneyiniz.",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public static ResponseEntity<ExceptionDTO<String>> rateLimitExceededException(RateLimitExceededException rateLimitExceededException, HttpServletRequest request){
        logger.warning(rateLimitExceededException.getMessage());

        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Maksimum istek sayısına ulaştınız. Lütfen daha sonra tekrar deneyiniz.",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyActivatedException.class)
    public static ResponseEntity<ExceptionDTO<String>> alreadyActivatedException(AlreadyActivatedException alreadyActivatedException, HttpServletRequest request){
        logger.warning(alreadyActivatedException.getMessage());

        return new ResponseEntity<>(new ExceptionDTO<>(HttpStatus.BAD_REQUEST.value(), "Hesap zaten daha önce aktif edilmiş.",
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

}
