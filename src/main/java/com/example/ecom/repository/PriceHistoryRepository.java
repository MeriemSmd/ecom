package com.example.ecom.repository;

import com.example.ecom.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
    PriceHistory findByCurrentPriceAndProductId(boolean currentPrice, int idProduct);
    List<PriceHistory> findByProductId(int idProduct);
}
