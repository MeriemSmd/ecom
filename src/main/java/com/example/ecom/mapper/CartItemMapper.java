package com.example.ecom.mapper;

import com.example.ecom.dto.CartItemDto;
import com.example.ecom.entity.CartItem;

import java.util.Set;
import java.util.stream.Collectors;

public class CartItemMapper {

    public static CartItemDto toDto(CartItem cartItem){
        return new CartItemDto(cartItem.getProduct().getId(),cartItem.getQuantity());
    }

    public static Set<CartItemDto> toDtos(Set<CartItem> cartItems){
        return cartItems.stream().map(CartItemMapper::toDto).collect(Collectors.toSet());
    }
}
