package com.restful.microservices.example.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restful.microservices.example.response.Product;
import com.restful.microservices.example.service.ProductService;
import com.restful.microservices.example.service.response.ProductResponse;

@RestController
@RequestMapping("/db")
public class ProductServiceDatabaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	Environment environment;

	@RequestMapping(value = { "{db}/listproducts" }, method = RequestMethod.GET)
	public Collection<Resource<ProductResponse>> listAllProducts(@PathVariable(value = "db") String db) {
		if (db.equals("in"))
			return listAllProducts();
		else if (db.equals("es"))
			return listAllProductsES();
		else if (db.equals("mn"))
			return listAllProductsMongo();

		return null;
	}

	@RequestMapping(value = { "/in/listproducts" }, method = RequestMethod.GET)
	public Collection<Resource<ProductResponse>> listAllProducts() {
		List<ProductResponse> listProducts = productService.listAllProducts();
		listProducts.forEach(p -> p.setPort(Integer.parseInt(Optional.ofNullable(environment.getProperty("server.port"))
				.orElse(environment.getProperty("local.server.port")))));
		List<Resource<ProductResponse>> resources = listProducts.stream().map(temp -> getAlbumResource(temp))
				.collect(Collectors.toList());
		return resources;
	}

	@RequestMapping(value = { "/es/listproducts" }, method = RequestMethod.GET)
	public Collection<Resource<ProductResponse>> listAllProductsES() {
		List<ProductResponse> listProducts = productService.listAllProductsES();
		listProducts.forEach(p -> p.setPort(Integer.parseInt(Optional.ofNullable(environment.getProperty("server.port"))
				.orElse(environment.getProperty("local.server.port")))));
		List<Resource<ProductResponse>> resources = listProducts.stream().map(temp -> getAlbumResourceES(temp))
				.collect(Collectors.toList());
		return resources;
	}

	@RequestMapping(value = { "/mn/listproducts" }, method = RequestMethod.GET)
	public Collection<Resource<ProductResponse>> listAllProductsMongo() {
		List<ProductResponse> listProducts = productService.listAllProductsMongo();
		listProducts.forEach(p -> p.setPort(Integer.parseInt(Optional.ofNullable(environment.getProperty("server.port"))
				.orElse(environment.getProperty("local.server.port")))));
		List<Resource<ProductResponse>> resources = listProducts.stream().map(temp -> getAlbumResourceMN(temp))
				.collect(Collectors.toList());
		return resources;
	}

	private Resource<ProductResponse> getAlbumResource(ProductResponse prodResponse) {
		Resource<ProductResponse> resource = new Resource<ProductResponse>(prodResponse);
		// Link to Product
		resource.add(linkTo(methodOn(ProductServiceDatabaseController.class).getProduct(prodResponse.getProductId()))
				.withSelfRel());
		return resource;
	}
	
	private Resource<ProductResponse> getAlbumResourceES(ProductResponse prodResponse) {
		Resource<ProductResponse> resource = new Resource<ProductResponse>(prodResponse);
		// Link to Product
		resource.add(linkTo(methodOn(ProductServiceDatabaseController.class).getProductES(prodResponse.getProductId()))
				.withSelfRel());
		return resource;
	}
	
	private Resource<ProductResponse> getAlbumResourceMN(ProductResponse prodResponse) {
		Resource<ProductResponse> resource = new Resource<ProductResponse>(prodResponse);
		// Link to Product
		resource.add(linkTo(methodOn(ProductServiceDatabaseController.class).getProductMongo(prodResponse.getProductId()))
				.withSelfRel());
		return resource;
	}

	@RequestMapping(value = { "/in/addproduct" }, method = RequestMethod.POST)
	public Object addProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@RequestMapping(value = { "/es/addproduct" }, method = RequestMethod.POST)
	public Object addProductES(@RequestBody Product product) {
		return productService.saveProductES(product);
	}

	@RequestMapping(value = { "/mn/addproduct" }, method = RequestMethod.POST)
	public Object addProductMN(@RequestBody Product product) {
		return productService.saveProductMongo(product);
	}

	@RequestMapping(value = { "/in/deleteproduct" }, method = RequestMethod.POST)
	public Object deleteProduct(@RequestBody Product product) {
		return productService.deleteProduct(product);
	}

	@RequestMapping(value = { "/es/deleteproduct" }, method = RequestMethod.POST)
	public Object deleteProductES(@RequestBody Product product) {
		return productService.deleteProductES(product);
	}

	@RequestMapping(value = { "/mn/deleteproduct" }, method = RequestMethod.POST)
	public Object deleteProductMongo(@RequestBody Product product) {
		return productService.deleteProductMongo(product);
	}

	@RequestMapping(value = { "/in/getproduct/{id}" }, method = RequestMethod.GET, produces = { "application/hal+json" })
	public Resource<ProductResponse> getProduct(@PathVariable String id) {
		ProductResponse product = productService.getProduct(id);
		product.setPort(Integer.parseInt(Optional.ofNullable(environment.getProperty("server.port"))
				.orElse(environment.getProperty("local.server.port"))));
		Resource<ProductResponse> result = new Resource(product);
		result.add(linkTo(methodOn(ProductServiceDatabaseController.class).listAllProducts()).withRel("allProducts"));
		result.add(linkTo(methodOn(ProductServiceDatabaseController.class).getProduct(id)).withSelfRel());
		return result;
	}

	@RequestMapping(value = { "/es/getproduct/{id}" }, method = RequestMethod.GET, produces = { "application/hal+json" })
	public Resource<ProductResponse> getProductES(@PathVariable String id) {
		ProductResponse product = productService.getProductES(id);
		product.setPort(Integer.parseInt(Optional.ofNullable(environment.getProperty("server.port"))
				.orElse(environment.getProperty("local.server.port"))));
		Resource<ProductResponse> result = new Resource(product);
		result.add(linkTo(methodOn(ProductServiceDatabaseController.class).listAllProductsES()).withRel("allProducts"));
		result.add(linkTo(methodOn(ProductServiceDatabaseController.class).getProductES(id)).withSelfRel());
		return result;
	}

	@RequestMapping(value = { "/mn/getproduct/{id}" }, method = RequestMethod.GET, produces = { "application/hal+json" })
	public Resource<ProductResponse> getProductMongo(@PathVariable String id) {
		ProductResponse product = productService.getProductMong(id);
		product.setPort(Integer.parseInt(Optional.ofNullable(environment.getProperty("server.port"))
				.orElse(environment.getProperty("local.server.port"))));
		Resource<ProductResponse> result = new Resource(product);
		result.add(linkTo(methodOn(ProductServiceDatabaseController.class).listAllProductsMongo()).withRel("allProducts"));
		result.add(linkTo(methodOn(ProductServiceDatabaseController.class).getProductMongo(id)).withSelfRel());
		return result;
	}
}
