package com.example.ecom.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;
    private double price;
    private boolean currentPrice;
    private LocalDateTime startDate;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Product product;
}
