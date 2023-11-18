package com.example.ecom.service;

import com.example.ecom.dto.CartDto;
import com.example.ecom.dto.CartItemDto;
import com.example.ecom.entity.*;
import com.example.ecom.exceptions.CartException;
import com.example.ecom.repository.CartRrepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest{
    @Mock
    CartRrepository cartRrepository;
    @Mock
    ProductService productService;
    @Mock
    AppUserService appUserService;
    @InjectMocks
    CartService cartService;
    PriceHistory priceHistory = PriceHistory.builder().currentPrice(true).Id(1).price(10).startDate(LocalDateTime.now()).build();
    Product product = Product.builder().id(1).name("Product 1").priceHistories(List.of(priceHistory)).build();
    CartItem cartItem = CartItem.builder().id(1).quantity(3).product(product).build();
    Cart cart = Cart.builder().id(1).items(Set.of(cartItem)).build();
    Optional<AppUser> user = Optional.of(AppUser.builder().name("user").id(1).roles(List.of("user")).build());
    CartDto cartDto = new CartDto(1,Set.of(new CartItemDto(1,3)));

    @Test
    public void testgetCartTotalPriceOK() {
        when(cartRrepository.findByIdAndUserName(anyInt(),anyString())).thenReturn(Optional.of(cart));
        assertEquals(30, cartService.getCartTotalPrice(1, "user"));
    }

    @Test
    public void testgetCartDtoByIdOK() {
        when(cartRrepository.findByIdAndUserName(anyInt(),anyString())).thenReturn(Optional.of(cart));
        assertEquals(cart.getId(), cartService.getCartDtoById(1,"user").idCart());
    }

    @Test
    public void testgetCartDtoByIdK0() {
        when(cartRrepository.findByIdAndUserName(anyInt(),anyString())).thenReturn(Optional.empty());
        assertThrows(CartException.class, () -> cartService.getCartDtoById(1,"user"));
    }
    @Test
    public void testgetCartByIddOK() {
        when(cartRrepository.findByIdAndUserName(anyInt(),anyString())).thenReturn(Optional.of(cart));
        assertEquals(cart, cartService.getCartById(1,"user"));
    }

    @Test
    public void testgetCartByIddK0() {
        when(cartRrepository.findByIdAndUserName(anyInt(),anyString())).thenReturn(Optional.empty());
        assertThrows(CartException.class, () -> cartService.getCartById(1,"user"));
    }
    @Test
    public void testaddCartExistinCart(){
        when(productService.getProductById(anyInt())).thenReturn(product);
        when(appUserService.findByName(anyString())).thenReturn(user);
        when(cartRrepository.findByIdAndUserName(anyInt(),anyString())).thenReturn(Optional.of(cart));
        when(cartRrepository.save(any())).thenReturn(cart);
        assertEquals(cartDto,cartService.addCart(cartDto, "user"));
    }
    @Test
    public void testaddCartNewCart(){
        cartDto = new CartDto(0,Set.of(new CartItemDto(1,3)));
        when(productService.getProductById(anyInt())).thenReturn(product);
        when(appUserService.findByName(anyString())).thenReturn(user);
        when(cartRrepository.save(any())).thenReturn(cart);
        assertEquals(new CartDto(1,Set.of(new CartItemDto(1,3))),cartService.addCart(cartDto,"user" ));
    }
}
