package com.tms.exceptions;

public class AgeException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Wrong age! Should be more than 18 ...";
    }
}
