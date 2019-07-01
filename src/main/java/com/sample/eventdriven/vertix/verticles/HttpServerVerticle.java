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
		router.get("/product").handler(this::getAllProducts);
		router.get("/product/:id").handler(this::getProduct);
		
		router.route().handler(BodyHandler.create());
		router.post("/product").handler(this::createProduct);
		
		vertx.createHttpServer().requestHandler(router).listen(port);

	}

	// send request on event-bus "CREATE_PRODUCT"
	private void createProduct(RoutingContext routingContext) {
		JsonObject request = routingContext.getBodyAsJson();
		
		//make non-blocking call, and wait for callback. Reply handler is inline
		//EventBus send(String address, Object message, Handler<AsyncResult<Message<T>>> replyHandler);
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
	
	// send request on event-bus "GET_ALL_PRODUCT"
	private void getAllProducts(RoutingContext routingContext) {
		
		//make non-blocking call, and wait for callback. Reply handler is inline
		//EventBus send(String address, Object message, Handler<AsyncResult<Message<T>>> replyHandler);
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

	// send request on event-bus "GET_PRODUCT"
	private void getProduct(RoutingContext routingContext) {
		
		final Long id = Long.valueOf(routingContext.request().getParam("id"));
		
		//make non-blocking call, and wait for callback. Reply handler is inline
		//EventBus send(String address, Object message, Handler<AsyncResult<Message<T>>> replyHandler);
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

	
}
