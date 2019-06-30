package com.sample.eventdriven;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sample.eventdriven.vertix.verticles.HttpServerVerticle;
import com.sample.eventdriven.vertix.verticles.ProductVerticle;

import io.vertx.core.Vertx;

@SpringBootApplication
public class Application {

	private HttpServerVerticle httpServerVerticle;
	private ProductVerticle productVerticle;

	
	@Autowired
	public Application(final HttpServerVerticle httpServerVerticle, final ProductVerticle productVerticle) {
		this.httpServerVerticle=httpServerVerticle;
		this.productVerticle=productVerticle;
	}
	
	@PostConstruct
	public void deployVerticles() {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(httpServerVerticle);
		vertx.deployVerticle(productVerticle);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
