package com.example.ecom.dto;

import org.springframework.http.HttpStatus;
public record ErrorDto (String message, HttpStatus httpStatus){
}
