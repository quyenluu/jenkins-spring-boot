package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Product;
import com.example.models.ResponseObject;
import com.example.repositories.ProductRepository;


@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    //DI = Dependency Injection
    @Autowired
    private ProductRepository _productRepository; //@Autowired tạo 1 lần và sử dụng xuyên suốt, ProductRepository được tạo khi class chạy

    @GetMapping("")
    List<Product> getAllProducts() {
        return _productRepository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = _productRepository.findById(id);
        return foundProduct.isPresent() ? 
                ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "success", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "not found", "")
                );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        //validate product name
        List<Product> foundProducts = _productRepository.findByProductName(newProduct.getProductName().trim());
        if(foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseObject("failed", "Product name is already exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "success", _productRepository.save(newProduct))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updateProduct = _productRepository.findById(id).map(prod -> {
            prod.setProductName(product.getProductName());
            prod.setPrice(product.getPrice());
            prod.setYears(product.getYears());
            return _productRepository.save(prod);
        }).orElseGet(() -> {
            product.setId(id);
            return _productRepository.save(product);
        });

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "success", updateProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean exist = _productRepository.existsById(id);
        if(exist) {
            _productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", "")
            ); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "not found", "")
            );
        }
    }
}
