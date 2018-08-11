package com.restful.microservices.example.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.restful.microservices.example.response.Product;


@Service	
public class ProductService {

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("customRestTemplate")
	private RestTemplate customrestTemplate;
	
	public Object getAllProducts(String db) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		final ResponseEntity<Product[]> responseEntity = customrestTemplate.getForEntity(env.getProperty("database.product.service.url")+db+"/listproducts", Product[].class);
		Product[] objects = responseEntity.getBody();
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();
		//Product responseBody=new Product();
		//BeanUtils.copyProperties(response.getBody(), responseBody);
		return Arrays.asList(objects);
	}

}
