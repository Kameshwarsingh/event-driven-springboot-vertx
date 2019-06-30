package com.sample.eventdriven.vertix.verticles;

import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

@Component
public class HttpServerVerticle extends AbstractVerticle {

	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String APPLICATION_JSON = "application/json; charset=utf-8";

	@Override
	public void start() throws Exception {
		super.start();
		final int port = config().getInteger("http.port", 8080);

		Router router = Router.router(vertx);
		router.get("/product").handler(this::getAllPeople);
		router.get("/product/:id").handler(this::getPerson);
		
		router.route().handler(BodyHandler.create());
		router.post("/product").handler(this::createPerson);
		
		vertx.createHttpServer().requestHandler(router).listen(port);

	}

	private void getAllPeople(RoutingContext routingContext) {
		
		vertx.eventBus().<String>send(ProductVerticle.GET_ALL_PRODUCT,"",result-> {
			if (result.succeeded()) {
				routingContext.response().putHeader(CONTENT_TYPE_HEADER,APPLICATION_JSON)
				.setStatusCode(200)
				.end(result.result().body());
			} else {
				routingContext.response()
				.setStatusCode(500)
				.end();
			}
			
		});
	}

	private void getPerson(RoutingContext routingContext) {
		final Long id = Long.valueOf(routingContext.request().getParam("id"));
		vertx.eventBus().<String>send(ProductVerticle.GET_PRODUCT,id,result-> {
			if (result.succeeded()) {
				routingContext.response().putHeader(CONTENT_TYPE_HEADER,APPLICATION_JSON)
				.setStatusCode(200)
				.end(result.result().body());
			} else {
				routingContext.response()
				.setStatusCode(500)
				.end();
			}
			
		});
	}

	private void createPerson(RoutingContext routingContext) {
		JsonObject request = routingContext.getBodyAsJson();
		vertx.eventBus().<String>send(ProductVerticle.CREATE_PRODUCT,request,result-> {
			if (result.succeeded()) {
				routingContext.response().putHeader(CONTENT_TYPE_HEADER,APPLICATION_JSON)
				.setStatusCode(200)
				.end(Json.encodePrettily( result.result().body()));
			} else {
				routingContext.response()
				.setStatusCode(500)
				.end();
			}
			
		});

	}
}
