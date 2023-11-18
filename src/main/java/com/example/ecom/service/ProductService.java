package com.example.ecom.service;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.PriceHistory;
import com.example.ecom.entity.Product;
import com.example.ecom.exceptions.Exceptions;
import com.example.ecom.exceptions.ProductException;
import com.example.ecom.mapper.ProductMapper;
import com.example.ecom.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {
    private ProductRepository productRepository;
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Double getCurrentProductPrice(int idProduct) throws ProductException {
        return productRepository.findById(idProduct).map(Product::getCurrentPrice).orElseThrow(() -> new ProductException
                (Exceptions.NO_PRODUCT_FOUND.getMessage() + idProduct, HttpStatus.NOT_FOUND));
    }

    public ProductDto getProductByIdToDto(int idProduct) {
        return productRepository.findById(idProduct)
                .map(ProductMapper::entityToDto)
                .orElseThrow(() -> new ProductException
                        (Exceptions.NO_PRODUCT_FOUND.getMessage() + idProduct, HttpStatus.NOT_FOUND));
    }

    public Product getProductById(int idProduct) {
        return productRepository.findById(idProduct)
                .orElseThrow(() -> new ProductException
                        (Exceptions.NO_PRODUCT_FOUND.getMessage() + idProduct, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        PriceHistory priceHistory = PriceHistory.builder().price(productDto.price()).startDate(LocalDateTime.now()).currentPrice(true).build();
        Product pr = Product.builder().name(productDto.name()).priceHistories(List.of(priceHistory)).build();
        pr = productRepository.save(pr);
        return new ProductDto(pr.getId(), pr.getName(), pr.getCurrentPrice());

    }
}
