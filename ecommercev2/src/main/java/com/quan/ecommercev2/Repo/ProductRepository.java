package com.quan.ecommercev2.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quan.ecommercev2.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByNameContainingIgnoreCase(String query);
    
    List<Product> findByCategoryId(Long categoryId);
}
