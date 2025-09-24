package com.ecommerce.backend.repository;


import com.ecommerce.backend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{
}
