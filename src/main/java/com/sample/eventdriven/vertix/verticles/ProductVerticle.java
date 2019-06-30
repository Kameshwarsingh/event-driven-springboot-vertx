package com.sample.eventdriven.vertix.verticles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.eventdriven.db.service.ProductService;
import com.sample.eventdriven.domain.Product;
import com.sample.eventdriven.vertix.ProductRequest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

@Component
public class ProductVerticle extends AbstractVerticle{
static final String GET_PRODUCT="get-product";
static final String GET_ALL_PRODUCT="get-all-product";
static final String CREATE_PRODUCT="create-product";

private ProductService productService;
private ObjectMapper mapper = Json.mapper;

@Autowired
public ProductVerticle(final ProductService personService) {
	this.productService=personService;
}


public void start() throws Exception {
	super.start();
	vertx.eventBus().<String>consumer(GET_ALL_PRODUCT).handler(this::getAllProducts);
	vertx.eventBus().<Long>consumer(GET_PRODUCT).handler(this::getProduct);
	vertx.eventBus().<JsonObject>consumer(CREATE_PRODUCT).handler(this::createProduct);
	
}

private void getAllProducts(Message<String> message) {
	vertx.<String>executeBlocking(future ->{ 
		try {
			future.complete(mapper.writeValueAsString(productService.getAllProducts()));
		} catch (JsonProcessingException ex ) {
			future.fail(ex);
		}
	}, result -> {
		if(result.succeeded()) {
			message.reply(result.result());
		} else {
			message.reply(result.cause().toString());
		}
	
	});
}

private void getProduct(Message<Long> message) {
	Long id = message.body();
	vertx.<String>executeBlocking(future ->{ 
		try {
			future.complete(mapper.writeValueAsString(productService.getProduct(id)));
		} catch (JsonProcessingException ex ) {
			future.fail(ex);
		}
	}, result -> {
		if(result.succeeded()) {
			message.reply(result.result());
		} else {
			message.reply(result.cause().toString());
		}
	
	});
	
}

private void createProduct(Message<JsonObject> message) {
	JsonObject requestBody = message.body();
	ProductRequest productRequest = new ProductRequest();

	productRequest.setProductName(requestBody.getString("productName")); 
	productRequest.setProductType(requestBody.getString("productType"));
	productRequest.setCost(new Integer(requestBody.getString("cost"))); 
	
	vertx.<Product>executeBlocking(future->future.complete(productService.save(productRequest)), result-> {
		if(result.succeeded()) {
			JsonObject response = new JsonObject();
			response.put("id", result.result().getId());
			response.put("_link", new JsonObject().put("self", new JsonObject().put("href","/product"+result.result().getId())));
			message.reply(response);
			
		} else {
			message.reply(result.cause().toString());
		}
	});
}



}
