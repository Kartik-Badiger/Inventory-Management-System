package com.example.miniproject.repository;

import com.example.miniproject.model.Sales;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository

public interface SalesRepository extends MongoRepository<Sales,String> {
    Sales findByOrderIdStartingWith(String prefix);
}
//custom query
//query for retrieve orders created yesterday