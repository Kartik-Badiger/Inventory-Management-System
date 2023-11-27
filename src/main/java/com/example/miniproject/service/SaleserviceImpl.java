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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class SaleserviceImpl {


    @Autowired
    SalesRepository salesRepository;

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Sales> getSaleOrderById(String orderId) {
        Optional<Sales> optionalSale = salesRepository.findById(orderId);
        if (optionalSale.isPresent()) {
            Sales sale = optionalSale.get();
            return ResponseEntity.ok().body(sale);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Transactional
    public ResponseEntity<Sales> createSaleOrder(Sales sale) {
        try {
            // First, update the inventory quantities for each item in the sale
            for (Inventory item : sale.getItems()) {
                Optional<Inventory> optionalInventory = inventoryRepository.findById(item.getProductId());
                if (!optionalInventory.isPresent()) {
                    throw new IllegalArgumentException("Invalid product ID: " + item.getProductId());
                }
                Inventory inventory = optionalInventory.get();
                double updatedQty = inventory.getQty() - item.getQty();
                if (updatedQty < 0) {
                    throw new IllegalArgumentException("Insufficient quantity for product: " + inventory.getPName());
                }
                inventory.setQty(updatedQty);
                inventoryRepository.save(inventory);

                // Fetch pName and pricePerUnit for the item
                Optional<Products> optionalProducts = productRepository.findById(item.getProductId());
                if (optionalProducts.isPresent()) {
                    Products product = optionalProducts.get();
                    item.setPName(product.getPName());
                    item.setPricePerUnit(product.getPrice());
                }
            }
            // Then, save the new sale order to the database
            double totalPrice = 0.0;
            for (Inventory item : sale.getItems()) {
                totalPrice += item.getQty() * item.getPricePerUnit();
            }
            sale.setTotalPrice(totalPrice);
            Sales savedSale = salesRepository.save(sale);
            return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




//    @Autowired
//    TransactionImpl transactionimpl;

//    public String saleOrder(String id, int qty) {
//        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
//        if(optionalInventory.isPresent()){
//            Inventory inventory = optionalInventory.get();
//            Optional<Sales> optionalSales = salesRepository.findById(inventory.getProductId());
//            if(inventory.getQty()>=qty){
//                Sales sales = new Sales();
//                sales.setProductId(inventory.getProductId());
//                sales.setPName(inventory.getPName());
//                sales.setQty(qty);
//                sales.setTotalPrice(inventory.getPricePerUnit()*qty);
//                inventory.setQty(inventory.getQty()-qty);
////                transactionimpl.addTransaction("SaleOrder",inventory.getProductId());
//                salesRepository.save(sales);
//                inventoryRepository.save(inventory);
//                return "Order Placed Successfully";
//            }else if(qty==0){
//                return "Order Cannot be Placed";
//            }
//            return "Stock Not Available";
//        }
//        return "Product is Not Available";
//    }



    public void addItem(Sales sale, Inventory item) {
        if (sale == null || item == null) {
            throw new IllegalArgumentException("Sale or item cannot be null");
        }
        sale.getItems().add(item);
        sale.setTotalPrice(sale.getTotalPrice() + item.getQty() * item.getPricePerUnit());
    }

    public void addSaleOrder(Sales existingSale, Sales newSale) {
        if (existingSale == null || newSale == null) {
            throw new IllegalArgumentException("Existing sale and new sale must not be null");
        }
        for (Inventory item : newSale.getItems()) {
            addItem(existingSale, item);
        }
    }

    public ResponseEntity<List<Sales>> getAll() {
        try {
            List<Sales> sales = salesRepository.findAll();
            return ResponseEntity.ok(sales);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
