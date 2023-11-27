package com.example.miniproject.controller;

//import com.example.miniproject.model.Sales;
//import com.example.miniproject.service.SaleService;
//import com.example.miniproject.service.SaleserviceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequestMapping("/sales")
//@RestController
//public class SalesController extends BaseLoggerController{
//    @Autowired
//    SaleserviceImpl SaleserviceImpl;
//    @PostMapping("/")
//    public ResponseEntity<Sales> addSaleOrder(@RequestBody Sales newSale) {
//        Sales existingSale = SaleserviceImpl.getSaleOrderById(newSale.getOrderId()).getBody();
//        if (existingSale != null) {
//            SaleserviceImpl.addSaleOrder(existingSale, newSale);
//            return ResponseEntity.ok(existingSale);
//        } else {
//            Sales createdSale = SaleserviceImpl.createSaleOrder(newSale).getBody();
//            return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
//        }
//    }
//    @GetMapping("/all")
//    public ResponseEntity<List<Sales>> getSales(){
//        return ResponseEntity.ok(SaleserviceImpl.getAll().getBody());
//    }
//    @GetMapping("/{orderId}")
//    public ResponseEntity<Sales> getSaleOrderById(@PathVariable String orderId) {
//        Sales sale = SaleserviceImpl.getSaleOrderById(orderId).getBody();
//        if (sale != null) {
//            return new ResponseEntity<>(sale, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @PostMapping("")
//    public ResponseEntity<Sales> createSaleOrder(@RequestBody Sales sale) {
//        try {
//            Sales savedSale = SaleserviceImpl.createSaleOrder(sale).getBody();
//            return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}


import com.example.miniproject.model.Sales;
import com.example.miniproject.service.SaleService;
import com.example.miniproject.service.SaleserviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/sales")
@RestController
public class SalesController extends BaseLoggerController {
    @Autowired
    SaleserviceImpl SaleserviceImpl;

    @PostMapping("/")
    public ResponseEntity<Sales> addSaleOrder(@RequestBody Sales newSale) {
        logger.info("Adding new sale order: {}", newSale);
        Sales existingSale = SaleserviceImpl.getSaleOrderById(newSale.getOrderId()).getBody();
        if (existingSale != null) {
            SaleserviceImpl.addSaleOrder(existingSale, newSale);
            return ResponseEntity.ok(existingSale);
        } else {
            Sales createdSale = SaleserviceImpl.createSaleOrder(newSale).getBody();
            logger.info("Created new sale order: {}", createdSale);
            return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sales>> getSales() {
        logger.info("Getting all sales");
        return ResponseEntity.ok(SaleserviceImpl.getAll().getBody());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Sales> getSaleOrderById(@PathVariable String orderId) {
        logger.info("Getting sale order by ID: {}", orderId);
        Sales sale = SaleserviceImpl.getSaleOrderById(orderId).getBody();
        if (sale != null) {
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Sales> createSaleOrder(@RequestBody Sales sale) {
        logger.info("Creating new sale order: {}", sale);
        try {
            Sales savedSale = SaleserviceImpl.createSaleOrder(sale).getBody();
            logger.info("Created new sale order: {}", savedSale);
            return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
//search api
//Date created updated
