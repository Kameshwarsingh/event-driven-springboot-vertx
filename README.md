## Event-driven and non-blocking
Idea of this sample program is to illustrated event driven and non blocking (asynchronous) programming using Vert.x and Spring-boot. 
This application uses Spring-boot to create standalone http service.
POST method is to to create Product, GET method to retrieve Product.


1.  Spring-boot: 	Used for creating stand-alone, spring-boot based application
2.  Vert.x: 	Used for event-driven (asynchronous) communication between “http-request handler/service” and “db/persistent handler”.
3.  H2 db (embedded): Used for storing product information.


### Classes and responsibilities
1.	Application: This class starts Spring-boot application and also initiates the verticles.
2.	HttpServerVerticle: This verticle listens to http, maps route/uri to handler, and forwards requests (non-blocking) to ProductVerticle.
3.	ProductVerticle: This verticle listens for non-blocking request from "HttpServerVerticle". It creates/fetches Product from db.
4.	ProductService: This service persists/fetches product information in db (h2 database)

Vertx is used to build the event-driven/non-blocking interaction between components.


### Command to add and fetch product.
  1.  curl -v -X "POST" -H "Content-Type: application/json" -d @test-data1.json  http://127.0.0.1:8080/product
  2.  curl -v -X "POST" -H "Content-Type: application/json" -d @test-data2.json  http://127.0.0.1:8080/product
  3.  curl -v -X "GET"  http://127.0.0.1:8080/product
  4.  curl -v -X "GET"  http://127.0.0.1:8080/product/1
  5.  curl -v -X "GET"  http://127.0.0.1:8080/product/2


### References
1.  https://vertx.io/
2.  https://spring.io/projects/spring-boot
3.  https://www.h2database.com/html/main.html
