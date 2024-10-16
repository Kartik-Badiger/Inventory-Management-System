package com.example.miniproject.service;


import com.example.miniproject.model.Inventory;
import com.example.miniproject.model.Products;
import com.example.miniproject.repository.InventoryRepository;
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

class InventoryServiceImplTest {

    @InjectMocks
    InventoryServiceImpl inventoryServiceImpl;

    @Mock
    InventoryRepository inventoryRepository;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetElementById_Success() {
        // Arrange
        Inventory inventory = new Inventory();
        inventory.setProductId("P001");
        when(inventoryRepository.findById("P001")).thenReturn(Optional.of(inventory));

        // Act
        ResponseEntity<Inventory> response = inventoryServiceImpl.getElementById("P001");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetElementById_NotFound() {
        // Arrange
        when(inventoryRepository.findById("P001")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Inventory> response = inventoryServiceImpl.getElementById("P001");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPurchaseOrder_Success() {
        // Arrange
        Products product = new Products();
        product.setProductId("P001");
        product.setPrice(100.0);
        product.setPName("Test Product");

        when(productRepository.findById("P001")).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<String> response = inventoryServiceImpl.purchaseOrder("P001", 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New order created", response.getBody());
    }
}

