package com.spring.SpringBoot.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.SpringBoot.Models.Product;
import com.spring.SpringBoot.Models.ResponseObject;
import com.spring.SpringBoot.Service.ProductService;

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
    private ProductService productService;

    private ResponseEntity<ResponseObject> responseEntity(HttpStatus httpStatus, String status, String message,
            Object data) {
        return ResponseEntity.status(httpStatus).body(new ResponseObject(status, message, data));
    }

    @GetMapping("")
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    // get product by id
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findByID(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        return product.isPresent() ? responseEntity(HttpStatus.OK, "ok", "Query Success", product)
                : responseEntity(HttpStatus.NOT_FOUND, "failed", "Cannot find product with id " + id, product);
    }

    // insert product with post method
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertProdEntity(@RequestBody Product product) {
        List<Product> foundProducts = productService.findByProductName(product.getProductName());

        if (foundProducts.size() > 0) {
            return responseEntity(HttpStatus.OK, "failed", "Product name already taken", "");
        }
        return responseEntity(HttpStatus.OK, "ok", "Insert product success",
                productService.insertProducts(product));
    }

    // update, upsert = update if found, otherwise insert
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProdEntity(@PathVariable Long id, @RequestBody Product newProduct) {
        List<Product> foundProducts = productService.findByProductName(newProduct.getProductName());

        if (foundProducts.size() > 0) {
            return responseEntity(HttpStatus.OK, "failed", "Product name already taken", "");
        } else {
            Product updateProdEntity = productService.updateProduct(id, newProduct);
            return responseEntity(HttpStatus.OK, "ok", "Update product success", updateProdEntity);
        }
    }

    // delete a product
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProdEntity(@PathVariable Long id) {
        boolean exists = productService.deleteProduct(id);

        if (exists) {
            return responseEntity(HttpStatus.OK, "ok", "Delete product " + id + " success", "");
        }
        return responseEntity(HttpStatus.NOT_FOUND, "failed", "Cannot find product to delete, id " + id, "");
    }
}
