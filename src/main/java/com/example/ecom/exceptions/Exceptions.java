package com.example.ecom.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Exceptions {
    NO_USER_FOUND("No user found with username  "),
    NO_CART_FOUND("No cart was found with idCart "),
    NO_PRODUCT_FOUND("No product found with Id ");
    private String message;

}
