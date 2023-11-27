package com.example.miniproject.repository;

import com.example.miniproject.model.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Products,String> {

}
