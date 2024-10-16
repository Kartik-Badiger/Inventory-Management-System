package com.example.miniproject.service;



import com.example.miniproject.model.Inventory;
import com.example.miniproject.model.Products;
import com.example.miniproject.model.Sales;
import com.example.miniproject.repository.InventoryRepository;
import com.example.miniproject.repository.ProductRepository;
import com.example.miniproject.repository.SalesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SalesServiceImplTest {

    @InjectMocks
    SaleserviceImpl saleserviceImpl;

    @Mock
    SalesRepository salesRepository;

    @Mock
    InventoryRepository inventoryRepository;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSaleOrderById_Success() {
        // Arrange
        Sales sale = new Sales();
        sale.setOrderId("1234");
        when(salesRepository.findById("1234")).thenReturn(Optional.of(sale));

        // Act
        ResponseEntity<Sales> response = saleserviceImpl.getSaleOrderById("1234");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1234", response.getBody().getOrderId());
    }

    @Test
    void testGetSaleOrderById_NotFound() {
        // Arrange
        when(salesRepository.findById("1234")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Sales> response = saleserviceImpl.getSaleOrderById("1234");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateSaleOrder_Success() {
        // Arrange
        Sales sale = new Sales();
        Inventory item = new Inventory();
        item.setProductId("P001");
        item.setQty(5);
        sale.getItems().add(item);

        Products product = new Products();
        product.setProductId("P001");
        product.setPName("Product1");
        product.setPrice(50.0);

        when(productRepository.findById("P001")).thenReturn(Optional.of(product));
        when(inventoryRepository.findById("P001")).thenReturn(Optional.of(item));
        when(salesRepository.save(any(Sales.class))).thenReturn(sale);

        // Act
        ResponseEntity<Sales> response = saleserviceImpl.createSaleOrder(sale);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateSaleOrder_InsufficientStock() {
        // Arrange
        Sales sale = new Sales();
        Inventory item = new Inventory();
        item.setProductId("P001");
        item.setQty(10);
        sale.getItems().add(item);

        Inventory inventory = new Inventory();
        inventory.setProductId("P001");
        inventory.setQty(5);

        when(inventoryRepository.findById("P001")).thenReturn(Optional.of(inventory));

        // Act
        ResponseEntity<Sales> response = saleserviceImpl.createSaleOrder(sale);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}

