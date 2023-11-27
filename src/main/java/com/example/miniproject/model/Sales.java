package com.example.miniproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.UUID;


@Document(collection = "Sales")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Sales {

    @Id
    private String orderId = "SO-"+UUID.randomUUID().toString();
    private List<Inventory> items;
    private double totalPrice;


}

