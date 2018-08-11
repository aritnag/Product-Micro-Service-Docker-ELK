package com.restful.microservices.example.entity;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ProductMongo {

	@Id
	private String id;
	private String productName;
	private String productType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
}
