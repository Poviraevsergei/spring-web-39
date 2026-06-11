package com.tms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<HttpStatus> userUpdateException(UserUpdateException ex) {
        log.error("User update exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<HttpStatus> fileNotFoundException(FileNotFoundException ex) {
        log.error("File not found exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<HttpStatus> fileException(FileException ex) {
        log.error("File exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpStatus> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Validation incorrect: " + ex.getBindingResult());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpStatus> methodUserNotFoundExceptionException(UserNotFoundException ex) {
        log.warn("User not found: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<HttpStatus> registrationException(RegistrationException ex) {
        log.error("Registration exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpStatus> usernameNotFoundException(UsernameNotFoundException ex) {
        log.error("UsernameNotFoundException: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
