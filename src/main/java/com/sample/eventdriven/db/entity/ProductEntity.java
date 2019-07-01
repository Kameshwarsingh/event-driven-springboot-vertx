package com.sample.eventdriven.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sample.eventdriven.vertix.ProductRequest;


@Entity
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String productName;
	private String productType;
	private Integer cost;

	public ProductEntity() {
	}

	public ProductEntity(String productName, String productType, Integer cost) {
		this.productName = productName;
		this.productType = productType;
		this.cost = cost;

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


	@Override
	public String toString() {
		return "ProductEntity{" + "id=" + id + ", productName=\'" + productName + "\',productType=\'" + productType + "\'}";
	}
	
	
}
