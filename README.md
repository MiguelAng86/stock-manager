# stock-manager

## Getting started

### How to run the application
1 - Compile and package the project
    
```
 ./mvnw clean package 
```

2 - Execute the .jar file compiled
```
 java -jar target/stock-manager-0.0.1-SNAPSHOT.jar
```

### Endpoints
- Get List of products
  - http://localhost:8080/v1/products
- Get List of products with stock
  - http://localhost:8080/v1/products/stock
- Open API Swagger documentation
  - http://localhost:8080/swagger-ui/index.html#/