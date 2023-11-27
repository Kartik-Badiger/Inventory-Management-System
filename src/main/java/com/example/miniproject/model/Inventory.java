package com.example.miniproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Inventory")
@Data
public class Inventory {
    @Id
    private String productId;
    private String pName;
    private double qty;
    private double pricePerUnit;

}
//orderlines should be added
//salesorder