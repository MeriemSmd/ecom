package com.example.ecom.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PriceHistoryDto(String name, LocalDateTime startDate, double price, boolean currentPrice) {
}
