package com.restful.microservices.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.restful.microservices.example.entity.ProductES;
import com.restful.microservices.example.entity.ProductMongo;
import com.restful.microservices.example.repository.ProductRepository;
import com.restful.microservices.example.repository.ProductRepositoryES;
import com.restful.microservices.example.repository.ProductRepositoryMongo;
import com.restful.microservices.example.response.Product;
import com.restful.microservices.example.service.ProductService;
import com.restful.microservices.example.service.response.ProductResponse;



@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private ProductRepositoryMongo productRepoMongo;
	
	@Autowired
	private ProductRepositoryES productRepoES;

	@Autowired
	Environment environment;

	@Override
	public ProductResponse getProduct(Long id) {
		com.restful.microservices.example.entity.Product prod = prodRepo.getOne(String.valueOf(id));
		ProductResponse returnProd = new ProductResponse();
		BeanUtils.copyProperties(prod, returnProd);
		String deployedPort = (deployedPort = environment.getProperty("server.port")) == null ? deployedPort
				: environment.getProperty("local.server.port");
		returnProd.setPort(Integer.parseInt(deployedPort));
		return returnProd;
	}
	
	@Override
	public ProductResponse getProductMong(String id) {
		Optional<ProductMongo> productMongo= productRepoMongo.findById(id);
		ProductResponse returnProd = new ProductResponse();
		if(productMongo.isPresent()){
			BeanUtils.copyProperties(productMongo.get(), returnProd);	
		}
		
		String deployedPort = (deployedPort = environment.getProperty("server.port")) == null ? deployedPort
				: environment.getProperty("local.server.port");
		returnProd.setPort(Integer.parseInt(deployedPort));
		return returnProd;
	}
	
	@Override
	public ProductResponse getProductES(String id) {
		Optional<ProductES> productES= productRepoES.findById(id);
		ProductResponse returnProd = new ProductResponse();
		if(productES.isPresent()){
			BeanUtils.copyProperties(productES.get(), returnProd);	
		}
		
		String deployedPort = (deployedPort = environment.getProperty("server.port")) == null ? deployedPort
				: environment.getProperty("local.server.port");
		returnProd.setPort(Integer.parseInt(deployedPort));
		return returnProd;
	}

	@Override
	public String saveProduct(Product product) {
		com.restful.microservices.example.entity.Product prod = new com.restful.microservices.example.entity.Product();
		BeanUtils.copyProperties(product, prod);
		prod = prodRepo.save(prod);
		return prod.getId();
	}
	
	@Override
	public String saveProductMongo(Product product) {
		ProductMongo prod=new ProductMongo();
		BeanUtils.copyProperties(product, prod);
		prod = productRepoMongo.save(prod);
		return prod.getId();
	}

	@Override
	public String saveProductES(Product product) {
		ProductES prod=new ProductES();
		BeanUtils.copyProperties(product, prod);
		prod = productRepoES.save(prod);
		return prod.getId();
	}
	
	private com.restful.microservices.example.entity.Product checkifProductPresent(Product product) {
		List<com.restful.microservices.example.entity.Product> existingProducts = prodRepo.findAll();
		for (com.restful.microservices.example.entity.Product prd : existingProducts) {
			if (null != product.getProductName() && prd.getProductName().equalsIgnoreCase(product.getProductName())
					&& null != product.getProductType()
					&& prd.getProductType().equalsIgnoreCase(product.getProductType()))
				return prd;
			String deployedPort = (deployedPort = environment.getProperty("server.port")) == null ? deployedPort
					: environment.getProperty("local.server.port");
			prd.setPort(Integer.parseInt(deployedPort));
		}
		return null;
	}
	
	private ProductMongo checkifProductPresentMongo(Product product) {
		List<ProductMongo> existingProducts = productRepoMongo.findAll();
		for (ProductMongo prd : existingProducts) {
			if (null != product.getProductName() && prd.getProductName().equalsIgnoreCase(product.getProductName())
					&& null != product.getProductType()
					&& prd.getProductType().equalsIgnoreCase(product.getProductType()))
				return prd;
		}
		return null;
	}
	
	private ProductES checkifProductPresentES(Product product) {
		Iterable<ProductES> existingProducts = productRepoES.findAll();
		for (ProductES prd : existingProducts) {
			if (null != product.getProductName() && prd.getProductName().equalsIgnoreCase(product.getProductName())
					&& null != product.getProductType()
					&& prd.getProductType().equalsIgnoreCase(product.getProductType()))
				return prd;
		}
		return null;
	}

	@Override
	public boolean deleteProduct(Product product) {
		com.restful.microservices.example.entity.Product prod = checkifProductPresent(product);
		if (null != prod) {
			prodRepo.delete(prod);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteProductMongo(Product product) {
		ProductMongo prod = checkifProductPresentMongo(product);
		if (null != prod) {
			productRepoMongo.delete(prod);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteProductES(Product product) {
		ProductES prod = checkifProductPresentES(product);
		if (null != prod) {
			productRepoES.delete(prod);
			return true;
		}
		return false;
	}
	@Override
	public List<ProductResponse> listAllProducts() {
		List<com.restful.microservices.example.entity.Product> products = prodRepo.findAll();
		List<ProductResponse> prods = products.stream().map(temp -> {
			ProductResponse obj = new ProductResponse();
			obj.setProductId(String.valueOf(temp.getId()));
			obj.setProductName(temp.getProductName());
			obj.setProductType(temp.getProductType());
			return obj;
		}).collect(Collectors.toList());
		return prods;
	}
	
	@Override
	public List<ProductResponse> listAllProductsMongo() {
		// List<ProductResponse> prods = new ArrayList<ProductResponse>();
		List<ProductMongo> products = productRepoMongo.findAll();
		List<ProductResponse> prods = products.stream().map(temp -> {
			ProductResponse obj = new ProductResponse();
			obj.setProductId(String.valueOf(temp.getId()));
			obj.setProductName(temp.getProductName());
			obj.setProductType(temp.getProductType());
			return obj;
		}).collect(Collectors.toList());
		return prods;
	}
	
	@Override
	public List<ProductResponse> listAllProductsES() {
		Iterable<ProductES> productsIterable = productRepoES.findAll();
		 List<ProductES> products = new ArrayList<ProductES>();
		 productsIterable.forEach(products::add);
		List<ProductResponse> prods = products.stream().map(temp -> {
			ProductResponse obj = new ProductResponse();
			obj.setProductId(String.valueOf(temp.getId()));
			obj.setProductName(temp.getProductName());
			obj.setProductType(temp.getProductType());
			return obj;
		}).collect(Collectors.toList());
		return prods;
	}
	

	@Override
	public ProductResponse getProduct(String id) {
		ProductResponse response = null;
		if (!StringUtils.isEmpty(id)) {
			com.restful.microservices.example.entity.Product prd = prodRepo.getOne(id);
			response = new ProductResponse();
			response.setProductName(prd.getProductName());
			response.setProductType(prd.getProductType());
			response.setProductId(String.valueOf(prd.getId()));
		}
		return response;
	}
}
