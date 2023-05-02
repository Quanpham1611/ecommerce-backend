package com.quan.ecommercev2.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quan.ecommercev2.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByLink(String link);
    Optional<User> findById(Integer id);
}
