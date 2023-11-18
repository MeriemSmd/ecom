package com.example.ecom.service;

import com.example.ecom.dto.PriceHistoryDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.PriceHistory;
import com.example.ecom.mapper.PriceHistoryMapper;
import com.example.ecom.mapper.ProductMapper;
import com.example.ecom.repository.PriceHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceHistoryService {
    PriceHistoryRepository priceHistoryRepository;
    ProductService productService;

    @Transactional
    public ProductDto setPrice(ProductDto productDto) {
        PriceHistory priceHistory = priceHistoryRepository.findByCurrentPriceAndProductId(true, productDto.idProduct());
        priceHistory.setCurrentPrice(false);
        PriceHistory current = PriceHistory.builder()
                .startDate(LocalDateTime.now())
                .currentPrice(true)
                .product(priceHistory.getProduct())
                .build();
        priceHistory.getProduct().getPriceHistories().add(current);
        return ProductMapper.entityToDto(productService.saveProduct(priceHistory.getProduct()));
    }

    public List<PriceHistoryDto> getAllPricesHistoryByProduct(int idProduct){
        return priceHistoryRepository.findByProductId(idProduct).stream().map(PriceHistoryMapper::entityToDto).collect(Collectors.toList());
    }
}
