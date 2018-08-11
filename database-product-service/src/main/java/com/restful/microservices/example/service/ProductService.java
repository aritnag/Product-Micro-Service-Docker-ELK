package com.restful.microservices.example.service;

import java.util.List;

import com.restful.microservices.example.response.Product;
import com.restful.microservices.example.service.response.ProductResponse;

public interface ProductService {

	public ProductResponse getProduct(Long id);

	public String saveProduct(Product product);

	public boolean deleteProduct(Product product);

	public List<ProductResponse> listAllProducts();

	public ProductResponse getProduct(String id);

	public ProductResponse getProductMong(String id);

	public String saveProductMongo(Product product);

	public boolean deleteProductMongo(Product product);

	public List<ProductResponse> listAllProductsMongo();

	public ProductResponse getProductES(String id);

	public String saveProductES(Product product);

	public boolean deleteProductES(Product product);

	public List<ProductResponse> listAllProductsES();
}
