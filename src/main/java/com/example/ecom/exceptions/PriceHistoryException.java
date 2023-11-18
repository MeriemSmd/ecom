package com.example.ecom.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class PriceHistoryException extends RuntimeException {
    private HttpStatus httpStatus;

    public PriceHistoryException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus =httpStatus;
    }
}
