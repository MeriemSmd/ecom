package com.example.ecom.mapper;

import com.example.ecom.dto.CartDto;
import com.example.ecom.dto.CartItemDto;
import com.example.ecom.entity.Cart;

import java.util.Set;

public class CartMapper {

    public static CartDto entityToDto(Cart cart) {
        Set<CartItemDto> cartItemsDto = CartItemMapper.toDtos(cart.getItems());
        return new CartDto(cart.getId(), cartItemsDto);
    }

}
