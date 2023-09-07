package com.example.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.models.Product;
import com.example.repositories.ProductRepository;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    //dùng H2 database (memory db) để tạo table Product khi khởi chạy ứng dụng và tạo 2 row data
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String ...args) throws Exception {
                Product product1 = new Product("Macbook Pro", 2023, 2400.0, "");
                Product product2 = new Product("Ipad Pro 2023", 2023, 1400.0, "");

                logger.info("insert data 1: " + productRepository.save(product1));
                logger.info("insert data 2: " + productRepository.save(product2));
            }
        };
    }
}
