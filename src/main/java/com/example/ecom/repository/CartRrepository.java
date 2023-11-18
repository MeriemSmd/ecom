package com.example.ecom.repository;

import com.example.ecom.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRrepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByIdAndUserName(int id, String userName);
}
