
//    @GetMapping("")
//error status code should be updated
//response entity
//dto classes
//exception handling
//@controlleradvice(exception at the central level)
//service layer implementation
//float fields
//mapstruct
//@mapper
//package com.example.miniproject.controller;
//
//import com.example.miniproject.model.Products;
//import com.example.miniproject.service.ProductServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RequestMapping("/product")
//@RestController
//public class ProductController extends BaseLoggerController{
//
//    @Autowired
//    ProductServiceImpl productServiceimpl;
//    @GetMapping("/all")
//    public ResponseEntity<List<Products>> getProducts(){
//        return productServiceimpl.allProducts();
//    }
//
//    @PostMapping
//    public ResponseEntity<Products> addProduct(@RequestBody Products products){
//        return productServiceimpl.addProduct(products);
//    }
//    @DeleteMapping("/{id}")
//    public void deleteproduct(@PathVariable("id") String id) {
//        // Code to delete the user with the specified id
//        productServiceimpl.delete(id);
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Products> updateproduct(@PathVariable("id") String id, @RequestBody Products products) {
//        return productServiceimpl.updateProduct(id,products);
//    }
//}


package com.example.miniproject.controller;

import com.example.miniproject.model.Products;
import com.example.miniproject.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/product")
@RestController
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductServiceImpl productServiceimpl;

    @GetMapping("/all")
    public ResponseEntity<List<Products>> getProducts() {
        List<Products> products = productServiceimpl.allProducts().getBody();
        logger.info("Retrieved {} products", products.size());
        return productServiceimpl.allProducts();
    }

    @PostMapping
    public ResponseEntity<Products> addProduct(@RequestBody Products products) {
        logger.info("Adding product {}", products);
        return productServiceimpl.addProduct(products);
    }

    @DeleteMapping("/{id}")
    public void deleteproduct(@PathVariable("id") String id) {
        productServiceimpl.delete(id);
        logger.info("Deleted product with id {}", id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateproduct(@PathVariable("id") String id, @RequestBody Products products) {
        logger.info("Updating product with id {} to {}", id, products);
        return productServiceimpl.updateProduct(id, products);
    }
}
