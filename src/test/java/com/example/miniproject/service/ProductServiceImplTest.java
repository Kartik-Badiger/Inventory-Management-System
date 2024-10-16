package com.example.miniproject.service;



import com.example.miniproject.model.Products;
import com.example.miniproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_ProductExists() {
        // Arrange
        Products product = new Products();
        product.setProductId("P001");

        when(productRepository.findById("P001")).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<Products> response = productServiceImpl.addProduct(product);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testAddProduct_ProductDoesNotExist() {
        // Arrange
        Products product = new Products();
        product.setProductId("P001");

        when(productRepository.findById("P001")).thenReturn(Optional.empty());
        when(productRepository.save(product)).thenReturn(product);

        // Act
        ResponseEntity<Products> response = productServiceImpl.addProduct(product);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        String productId = "P001";

        // Act
        ResponseEntity<String> response = productServiceImpl.delete(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

