package com.quan.ecommercev2.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quan.ecommercev2.Model.CartItem;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
}
