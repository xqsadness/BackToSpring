package com.spring.SpringBoot.Database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.SpringBoot.Repositories.ProductRepository;

@Configuration
public class Database {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Product prdA = new Product(1L, "Mac book pro", 2002, 2400000.0, "12");
                // Product prdB = new Product(2L, "Iphone", 2000, 240000.0, "123");
                // Product prdC = new Product(3L, "Ipad", 2005, 2400000.0, "123123");
                // productRepository.save(prdA);
                // productRepository.save(prdB);
                // productRepository.save(prdC);
            }
        };
    }

}
