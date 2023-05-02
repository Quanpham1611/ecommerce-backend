package com.quan.ecommercev2.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quan.ecommercev2.Model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    
}
