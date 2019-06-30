package com.sample.eventdriven.db.repository;

import org.springframework.data.repository.CrudRepository;

import com.sample.eventdriven.db.entity.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity,Long>{

}
