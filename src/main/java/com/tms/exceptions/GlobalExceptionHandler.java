package com.tms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;

@ControllerAdvice //глобальный обработчик исключений
public class GlobalExceptionHandler {
    @ExceptionHandler(AgeException.class)
    public ResponseEntity<HttpStatus> ageExceptionHandler(AgeException ex) {
        System.out.println("Age exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<HttpStatus> userUpdateExceptionHandler(UserUpdateException ex) {
        System.out.println("User update exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<HttpStatus> fileNotFoundExceptionHandler(FileNotFoundException ex) {
        System.out.println("File not found exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<HttpStatus> fileExceptionHandler(FileException ex) {
        System.out.println("File exception: " + ex.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
