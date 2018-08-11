package com.restful.microservices.example.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.restful.microservices.example.entity.ProductES;
import com.restful.microservices.example.entity.ProductMongo;

public interface ProductRepositoryES extends ElasticsearchRepository<ProductES, String>{

	@Query("{ '$and' : [ { 'productName' : ?0 , 'productType' : ?1 }]}")
	ProductMongo findByNameAndType(String productName,String productType);
}
