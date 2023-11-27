package com.example.miniproject.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Products {
    @Id
    private String productId;
    private String pName;
    private double price;
    private String category;

}
