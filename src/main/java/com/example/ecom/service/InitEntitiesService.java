package com.example.ecom.service;

import com.example.ecom.entity.AppUser;
import com.example.ecom.entity.PriceHistory;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class InitEntitiesService {
    private ProductRepository productRepository;
    private AppUserService appUserService;

    @EventListener(ApplicationReadyEvent.class)
    public void handleContextRefresh() {
        PriceHistory priceHistory = PriceHistory.builder().price(10).startDate(LocalDateTime.now()).currentPrice(true).build();
        Product p = Product.builder()
                .name("product 1")
                .priceHistories(new ArrayList<>())
                .build();
        p.addPriceHistory(priceHistory);
        productRepository.save(p);
        log.info("Stored a new Product " + p);
        AppUser user = AppUser.builder().name("user").password("user").roles(List.of("user")).build();
        appUserService.addUser(user);
        log.info("Stored a new user " + user);
        AppUser admin = AppUser.builder().name("admin").password("admin").roles(List.of("admin", "user")).build();
        appUserService.addUser(admin);
        log.info("Stored a new admin " + admin);
    }
}
