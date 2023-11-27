package com.example.miniproject.service;

import com.example.miniproject.model.Products;
import com.example.miniproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Override
    public ResponseEntity<List<Products>> allProducts() {
        try {
            List<Products> products = productRepository.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // to retrieve single product
    // structure to delete a multipl products
    @Override
    public ResponseEntity<Products> addProduct(Products products) {
        try {
            Optional<Products> product = productRepository.findById(products.getProductId());
            if (product.isPresent()) {
                // If the product already exists, return a conflict status
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                // Save the new product and return a success status
                Products newProduct = productRepository.save(products);
                return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
            }
        } catch (Exception e) {
            // If an exception occurs, return an internal server error status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<String> delete(String id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Products> updateProduct(String id, Products products) {
        Optional<Products> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            // update the product with the new data
            Products existingProduct = optionalProduct.get();
            existingProduct.setPName(products.getPName());
            existingProduct.setPrice(products.getPrice());
            existingProduct.setCategory(products.getCategory());
            // save the updated product to the database
            productRepository.save(existingProduct);

            return ResponseEntity.ok(existingProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
