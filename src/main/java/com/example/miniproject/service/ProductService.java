package com.example.miniproject.service;

import com.example.miniproject.model.Products;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    public ResponseEntity<List<Products>> allProducts();



    // to retrieve single product
    // structure to delete a multipl products
    ResponseEntity<Products> addProduct(Products products);

    ResponseEntity<String> delete(String id);

    ResponseEntity<Products> updateProduct(String id, Products products);
}
