package com.example.miniproject.service;

import com.example.miniproject.model.Inventory;
import com.example.miniproject.model.Products;
import com.example.miniproject.model.Sales;
import com.example.miniproject.repository.InventoryRepository;
import com.example.miniproject.repository.ProductRepository;
import com.example.miniproject.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SalesRepository salesRepo;

    @Override
    public ResponseEntity<List<Inventory>> getInventories() {
        try {
            List<Inventory> inventories = inventoryRepository.findAll();
            return new ResponseEntity<>(inventories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<String> delete(String id) {
        try {
            inventoryRepository.deleteById(id);
            return new ResponseEntity<>("Inventory item deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete inventory item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> purchaseOrder(String id, int qty) {
        try {
            Optional<Products> optionalProducts = productRepository.findById(id);
            if (optionalProducts.isEmpty()) {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
            Products products = optionalProducts.get();
            Optional<Inventory> optionalInventory = inventoryRepository.findById(products.getProductId());
            Inventory inventory;
            if (optionalInventory.isPresent()) {
                inventory = optionalInventory.get();
                inventory.setQty(inventory.getQty() + qty);
                inventoryRepository.save(inventory);
                Sales sale = new Sales();
                sale.setOrderId("PO-"+ products.getProductId());
                sale.setTotalPrice(qty*products.getPrice());
                Inventory item1 = new Inventory();
                item1.setProductId(products.getProductId());
                item1.setPName(products.getPName());
                item1.setQty(qty);
                item1.setPricePerUnit(products.getPrice());
                List<Inventory> itemsList = new ArrayList<>();
                itemsList.add(item1);
                sale.setItems(itemsList);
                salesRepo.save(sale);
                return new ResponseEntity<>("Existing order updated", HttpStatus.OK);
            }
            inventory = new Inventory();
            inventory.setProductId(products.getProductId());
            inventory.setPName(products.getPName());
            inventory.setQty(qty);
            inventory.setPricePerUnit(products.getPrice());
            inventoryRepository.save(inventory);
            Sales sale = new Sales();
            sale.setOrderId("PO-"+ products.getProductId());
            sale.setTotalPrice(qty*products.getPrice());
            Inventory item1 = new Inventory();
            item1.setProductId(products.getProductId());
            item1.setPName(products.getPName());
            item1.setQty(qty);
            item1.setPricePerUnit(products.getPrice());
            List<Inventory> itemsList = new ArrayList<>();
            itemsList.add(item1);
            sale.setItems(itemsList);
            salesRepo.save(sale);
            return new ResponseEntity<>("New order created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to process purchase order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<Inventory> getElementById(String id) {
        try {
            Optional<Inventory> inventory = inventoryRepository.findById(id);
            if (inventory.isPresent()) {
                return new ResponseEntity<>(inventory.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
