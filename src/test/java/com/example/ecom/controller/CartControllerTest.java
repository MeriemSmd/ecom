package com.example.ecom.controller;

import com.example.ecom.dto.CartDto;
import com.example.ecom.dto.CartItemDto;
import com.example.ecom.exceptions.CartException;
import com.example.ecom.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CartService cartService;
    @Autowired
    ObjectMapper objectMapper;
    CartDto cartDto = new CartDto(1, Set.of(new CartItemDto(1, 3)));

    private String base64Encode(String value) {
        return new String(Base64.getEncoder().encode(value.getBytes()));
    }

    @Test
    public void testGetTotalPrice200() throws Exception {
        Mockito.when(cartService.getCartTotalPrice(anyInt(),anyString() )).thenReturn(30.0);
        mockMvc.perform(get("/v1/carts/totalPrice/1")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode("user:user")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("30.0"));
    }

    @Test
    public void testGetTotalPrice404() throws Exception {
        Mockito.when(cartService.getCartTotalPrice(anyInt(),anyString() )).thenThrow(CartException.class);
        mockMvc.perform(get("/v1/carts/totalPrice/1").header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode("user:user")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testgetCartById200() throws Exception {
        Mockito.when(cartService.getCartDtoById(anyInt(),anyString())).thenReturn(cartDto);
        mockMvc.perform(get("/v1/carts/1").header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode("user:user")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(cartDto)));
    }

    @Test
    public void testgetCartById404() throws Exception {
        Mockito.when(cartService.getCartDtoById(anyInt(),anyString())).thenThrow(CartException.class);
        mockMvc.perform(get("/v1/carts/1").header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode("user:user")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testaddCartd201() throws Exception {
        Mockito.when(cartService.addCart(any(), anyString())).thenReturn(cartDto);
        mockMvc.perform(post("/v1/carts/")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + base64Encode("user:user"))
                        .content(objectMapper.writeValueAsString(cartDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(cartDto)));
    }
}
