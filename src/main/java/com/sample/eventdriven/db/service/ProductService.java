package com.sample.eventdriven.db.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.eventdriven.db.entity.ProductEntity;
import com.sample.eventdriven.db.repository.ProductRepository;
import com.sample.eventdriven.domain.Product;
import com.sample.eventdriven.vertix.ProductRequest;

@Service
public class ProductService {

private ProductRepository productRepository;

@Autowired
public ProductService(final ProductRepository personRepository ) {
	this.productRepository=personRepository;
}

public List<Product> getAllProducts(){
	return StreamSupport.stream(productRepository.findAll().spliterator(),false)
			.map(Product::new)
			.collect(Collectors.toList());
}

public Product getProduct(Long id) {
	return productRepository.findById(id).map(Product::new).orElseThrow(IllegalStateException::new);
}

public Product save(ProductRequest productRequest) {
	final ProductEntity personEntity = ProductEntity.from(productRequest);
	productRepository.save(personEntity);
	return new Product(personEntity);
}
	
}
