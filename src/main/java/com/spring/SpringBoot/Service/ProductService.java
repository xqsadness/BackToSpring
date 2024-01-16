package com.spring.SpringBoot.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.SpringBoot.Models.Product;
import com.spring.SpringBoot.Repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public List<Product> findByProductName(String productName) {
        return repository.findByProductName(productName.trim());
    }

    public Product insertProducts(Product product) {
        return repository.save(product);
    }

    // update if found, otherwise insert
    public Product updateProduct(Long id, Product newProduct) {
        return repository.findById(id)
                .map(product -> {
                    product.setProductName(newProduct.getProductName());
                    product.setProductYear(newProduct.getProductYear());
                    product.setUrlString(newProduct.getUrlString());
                    product.setPrice(newProduct.getPrice());

                    return repository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        boolean exists = repository.existsById(id);

        if (exists) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
