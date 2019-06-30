## Event-driven and non-blocking
Idea of this sample program is to illustrated event driven and non blocking (asynchronous) programming using Vert.x and Spring-boot. 
This application uses Spring-boot to create standalone http service. 
POST method is to used to create product information, GET method to retrieve product information.

1.  Spring-boot: 	Stand-alone, Spring-boot based application
2.  Vert.x: 	Event driven (asynchronous) communication between “http request handler service” and “db/persistent handler”.
3.  H2 db (embedded): Stores product information

## Primary classes and interactions
1.	Application: This class starts Spring-boot application. It loads two Verticles of Vertx.
2.	HttpServerVerticle: This vertx verticle listens to http and forwards requests to db/persistent Verticle.
3.	ProductVerticle: This vertx verticle manages db/persistent services, such as creating product in db, fetching product information from db.
4.	ProductService: This service persists/interacts with db H2
Vertx is used to build the event-driven/non-blocking interaction between components.

Use test data and curl commands to test.

## References
1.  https://vertx.io/
2.  https://spring.io/projects/spring-boot
3.  https://www.h2database.com/html/main.html
