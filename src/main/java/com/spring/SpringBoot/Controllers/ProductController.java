package com.spring.SpringBoot.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.SpringBoot.Models.Product;
import com.spring.SpringBoot.Models.ResponseObject;
import com.spring.SpringBoot.Repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("")
    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    // get product by id
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> finByID(@PathVariable Long id) {
        Optional<Product> product = repository.findById(id);

        return product.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query Success", product))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find product with id " + id, product));
    }

    // insert product with post method
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertProdEntity(@RequestBody Product product) {
        List<Product> foundProducts = repository.findByProductName(product.getProductName().trim());

        if (foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Product name already taken", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert product success", repository.save(product)));
    }

    // update, upsert = update if found, otherwise insert
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProdEntity(@PathVariable Long id, @RequestBody Product newProduct) {
        Product updateProdEntity = repository.findById(id)
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

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update product success ", updateProdEntity));
    }

    // delete a product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProdEntity(@PathVariable Long id) {
        boolean exists = repository.existsById(id);

        if (exists) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete product success ", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find product to delete, id " + id, ""));
    }
}
