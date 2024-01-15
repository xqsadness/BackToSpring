package com.spring.SpringBoot.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.SpringBoot.Models.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
} 