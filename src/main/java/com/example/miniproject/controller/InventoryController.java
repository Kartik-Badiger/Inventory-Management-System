package com.example.miniproject.controller;

import com.example.miniproject.model.Inventory;
import com.example.miniproject.service.InventoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/Inventory")
@RestController
public class InventoryController {
    private final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    InventoryServiceImpl inventoryServiceimpl;

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable("id") String id) {
        inventoryServiceimpl.delete(id);
        logger.info("Deleted inventory with id {}", id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getInventory() {
        List<Inventory> inventories = inventoryServiceimpl.getInventories().getBody();
        logger.info("Retrieved {} inventories", inventories.size());
        return inventoryServiceimpl.getInventories();
    }

    @PostMapping("/purchaseorder")
    public ResponseEntity<String> placeOrder(@RequestParam String id, @RequestParam int qty) {
        logger.info("Placing order for inventory with id {} and quantity {}", id, qty);
        return inventoryServiceimpl.purchaseOrder(id, qty);
    }

    @GetMapping("/id")
    public ResponseEntity<Inventory> getElement(@RequestParam String id) {
        ResponseEntity<Inventory> responseEntity = inventoryServiceimpl.getElementById(id);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            Inventory inventory = responseEntity.getBody();
            logger.info("Retrieved inventory with id {}", id);
        } else {
            logger.error("Failed to retrieve inventory with id {}", id);
        }
        return responseEntity;
    }
}
//mask the sensitive entries