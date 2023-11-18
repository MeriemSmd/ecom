package com.example.ecom.controller;

import com.example.ecom.dto.CartDto;
import com.example.ecom.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/carts/")
@Slf4j
public class CartController {
    CartService cartService;

    @GetMapping("totalPrice/{idCart}")
    public double getCartsTolalPrice(@PathVariable("idCart") int idCart, @AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getCartTotalPrice(idCart, userDetails.getUsername());
    }

    @GetMapping("{idCart}")
    public CartDto getCartById(@PathVariable("idCart") int idCart, @AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getCartDtoById(idCart, userDetails.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto addCart(@RequestBody CartDto cartDto, @AuthenticationPrincipal UserDetails userDetails) {
        return cartService.addCart(cartDto, userDetails.getUsername());

    }
}
