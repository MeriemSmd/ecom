package com.example.ecom.dto;

import java.util.Set;

public record CartDto(int idCart, Set<CartItemDto> productsQuatities) {
}
