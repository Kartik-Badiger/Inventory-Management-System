package com.example.miniproject.service;

import com.example.miniproject.model.Inventory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    ResponseEntity<List<Inventory>> getInventories();

    ResponseEntity<String> delete(String id);

    ResponseEntity<String> purchaseOrder(String id, int qty);

    ResponseEntity<Inventory> getElementById(String id);
}
