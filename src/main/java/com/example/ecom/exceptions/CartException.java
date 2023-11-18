package com.example.ecom.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class CartException extends RuntimeException {
    private HttpStatus httpStatus;

    public CartException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
