package com.example.ecom.mapper;

import com.example.ecom.dto.PriceHistoryDto;
import com.example.ecom.entity.PriceHistory;

public class PriceHistoryMapper {

    public static PriceHistoryDto entityToDto(PriceHistory priceHistory) {
        return PriceHistoryDto.builder()
                .name(priceHistory.getProduct().getName())
                .price(priceHistory.getPrice())
                .currentPrice(priceHistory.isCurrentPrice())
                .startDate(priceHistory.getStartDate())
                .build();
    }
}
