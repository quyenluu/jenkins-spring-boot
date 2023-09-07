package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.models.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}
