package com.tms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;

@Slf4j
@ControllerAdvice //глобальный обработчик исключений
public class GlobalExceptionHandler {
    @ExceptionHandler(AgeException.class)
    public ResponseEntity<HttpStatus> ageExceptionHandler(AgeException ex) {
        log.error("Age exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<HttpStatus> userUpdateExceptionHandler(UserUpdateException ex) {
        log.error("User update exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<HttpStatus> fileNotFoundExceptionHandler(FileNotFoundException ex) {
        log.error("File not found exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<HttpStatus> fileExceptionHandler(FileException ex) {
        log.error("File exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
