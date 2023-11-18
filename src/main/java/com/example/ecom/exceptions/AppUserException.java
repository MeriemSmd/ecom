package com.example.ecom.exceptions;

import org.springframework.http.HttpStatus;

public class AppUserException extends RuntimeException{
    private HttpStatus httpStatus;

    public AppUserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
