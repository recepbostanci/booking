
# Getting Started
The project is a Booking API that covered create, read, update and delete booking and create and delete block that is made for booking restriction.

In this project, we technically have benefits of :
* followed SOLID principle
* DI (w/ constructor injection)
* locator pattern for defining business rule services
* message delivery approach using RequextContext (w/ MessageBucket)
* Open Api to use services over swagger (w/ custom oas annotation of @Operation, and given example requests)
* All Api endpoints responses encapsulated with a custom response object (RestResponse)
* have custom exception
* have custom ErrorAttributes component to handle all exception for providing meaningful  error messages

Note: project start with ```dev``` profile default !
### To Start Application
```bash
./mvnw spring-boot:run
```

* [Application Swagger](http://localhost:8080/booking-app)

### Built With

* Java 17
* Spring Boot 2.6
* H2 DB
* Open Api
* Lombok
* Mapstruct

### What we didn't Implement and can be implemented in the future

- keep DB credentials in a config server, or Secrets in K8s, or Secret Manager in AWS as encrypted
- resource file to keep messages for providing multi-language support
- keep user infos at Spring Security Context using JWT
- create new db tables to keep propery infos etc.