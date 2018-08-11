package com.restful.microservices.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.restful.microservices.example.entity.ProductMongo;

@Repository
public interface ProductRepositoryMongo extends MongoRepository<ProductMongo, String> {
	
	@Query("{ '$and' : [ { 'productName' : ?0 , 'productType' : ?1 }]}")
	ProductMongo findByNameAndType(String productName,String productType);

}
