package com.example.ecom.controller;

import com.example.ecom.dto.PriceHistoryDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.service.PriceHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/priceHistory")
public class PriceHistoryCotroller {
    private PriceHistoryService priceHistoryService;

    @PutMapping("/{idProduct}")
    public ProductDto setPriceOfProduct(@PathVariable int idProduct, @RequestBody ProductDto productDto) {
        return priceHistoryService.setPrice(productDto);
    }
    @GetMapping("/{idProduct}")
    public List<PriceHistoryDto> getAllPricesHistoryByProduct(@PathVariable int idProduct){
        return priceHistoryService.getAllPricesHistoryByProduct(idProduct);
    }
}
