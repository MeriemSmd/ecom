package com.example.ecom.controller;

import com.example.ecom.dto.ErrorDto;
import com.example.ecom.exceptions.CartException;
import com.example.ecom.exceptions.PriceHistoryException;
import com.example.ecom.exceptions.ProductException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ProductException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleProductException(ProductException ex) {
        return new ErrorDto(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(PriceHistoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handlePriceHistoryException(PriceHistoryException ex) {
        return new ErrorDto(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(CartException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handlePCartException(CartException ex) {
        return new ErrorDto(ex.getMessage(), ex.getHttpStatus());
    }
}
