package com.example.ecom.service;


import com.example.ecom.dto.CartDto;
import com.example.ecom.entity.AppUser;
import com.example.ecom.entity.Cart;
import com.example.ecom.entity.CartItem;
import com.example.ecom.exceptions.AppUserException;
import com.example.ecom.exceptions.CartException;
import com.example.ecom.exceptions.Exceptions;
import com.example.ecom.mapper.CartMapper;
import com.example.ecom.repository.CartRrepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {
    CartRrepository cartRrepository;
    ProductService productService;
    AppUserService appUserService;

    public double getCartTotalPrice(int idCart, String userName) {
        return getCartById(idCart, userName).getItems()
                .stream()
                .mapToDouble(item -> item.getProduct().getCurrentPrice() * item.getQuantity()).sum();
    }

    public CartDto getCartDtoById(int idCart, String userName) {
        return cartRrepository.findByIdAndUserName(idCart, userName).map(CartMapper::entityToDto).orElseThrow(() -> new CartException(Exceptions.NO_CART_FOUND.getMessage()+ idCart, HttpStatus.NOT_FOUND));
    }

    public Cart getCartById(int idCart, String userName) {
        return cartRrepository.findByIdAndUserName(idCart, userName).orElseThrow(() -> new CartException(Exceptions.NO_CART_FOUND.getMessage()+ idCart, HttpStatus.NOT_FOUND));
    }

    public CartDto addCart(CartDto cartDto, String userName) {
        Set<CartItem> items = getCartItemsFromCartDto(cartDto);
        AppUser user = appUserService.findByName(userName).orElseThrow(() -> new AppUserException(Exceptions.NO_USER_FOUND.getMessage() + userName, HttpStatus.NOT_FOUND));
        Cart cart = cartDto.idCart() != 0 ?
                cartRrepository.findByIdAndUserName(cartDto.idCart(),userName).orElseThrow(() -> new CartException(Exceptions.NO_CART_FOUND.getMessage() + cartDto.idCart(), HttpStatus.NOT_FOUND))
                : Cart.builder().items(items).user(user).build();
        return CartMapper.entityToDto(cartRrepository.save(cart));
    }

    private Set<CartItem> getCartItemsFromCartDto(CartDto cartDto) {
        Set<CartItem> items = cartDto.productsQuatities().stream().map(p ->
                CartItem.builder().product(productService.getProductById(p.idProduct())).quantity(p.quatity()).build()
        ).collect(Collectors.toSet());
        return items;
    }
}
