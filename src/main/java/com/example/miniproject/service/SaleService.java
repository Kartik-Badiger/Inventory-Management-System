package com.example.miniproject.service;

import com.example.miniproject.model.Inventory;
import com.example.miniproject.model.Sales;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SaleService {

//    public String saleOrder(String id, int qty);
public ResponseEntity<Sales> getSaleOrderById(String orderId);
    public List<Sales> getAll();
    ResponseEntity<Sales> createSaleOrder(Sales sale);
    void addSaleOrder(Sales existingSale, Sales newSale);
    void addItem(Sales sale, Inventory item);
}
