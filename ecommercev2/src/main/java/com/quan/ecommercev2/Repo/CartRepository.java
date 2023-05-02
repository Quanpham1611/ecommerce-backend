package com.quan.ecommercev2.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.quan.ecommercev2.Model.Cart;
@EnableJpaRepositories

public interface CartRepository extends JpaRepository<Cart, Long>{
    Optional<Cart> findByUserId(int userId);
}
