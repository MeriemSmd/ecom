package com.example.ecom.mapper;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.Product;
import com.example.ecom.exceptions.PriceHistoryException;

public class ProductMapper {
    public static ProductDto entityToDto(Product product) throws PriceHistoryException {
        return new ProductDto(product.getId(), product.getName(), product.getCurrentPrice());
    }
}
