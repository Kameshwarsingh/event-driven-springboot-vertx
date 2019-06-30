package com.sample.eventdriven.domain;

import com.sample.eventdriven.db.entity.ProductEntity;

public class Product {

	private Long id;
	private String productName;
	private String productType;
	private Integer cost;

	public Product(ProductEntity personEntity) {
		this.id = personEntity.getId();
		this.productName = personEntity.getProductName();
		this.productType = personEntity.getProductType();
		this.cost = personEntity.getCost();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	

}
