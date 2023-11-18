package com.example.ecom.controller;


import com.example.ecom.dto.ProductDto;
import com.example.ecom.exceptions.ProductException;
import com.example.ecom.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {
    ProductService productService;

    @GetMapping("/{idProduct}")
    public ProductDto getProductById(@PathVariable("idProduct") int id) throws ProductException {
        return productService.getProductByIdToDto(id);
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }


}
