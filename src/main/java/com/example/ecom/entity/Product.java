package com.example.ecom.entity;

import com.example.ecom.exceptions.PriceHistoryException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
    private List<PriceHistory> priceHistories = new ArrayList<>();

    public void addPriceHistory(PriceHistory priceHistory){
        priceHistories.add(priceHistory);
        priceHistory.setProduct(this);
    }

    public double getCurrentPrice() {
        return priceHistories.stream()
                .filter(PriceHistory::isCurrentPrice)
                .mapToDouble(PriceHistory::getPrice)
                .findFirst()
                .orElseThrow(() -> new PriceHistoryException("No default price has been precised foe this product " + this, HttpStatus.NOT_FOUND));
    }
}
