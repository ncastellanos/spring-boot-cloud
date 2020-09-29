# Arquitectura de Microservicios


## Requirements

- Java 11 
- Spring Boot 2
- Gradle 
- CURL
- JQ
- Docker



### Microservice Product
GET

    curl -X GET http://localhost:8091/products  -H 'Accept: application/json' | jq '.'

POST

    curl  --request POST 'localhost:8091/products' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name":"Wallabee Men'\''s Suede Shoe",
    "description":"Comfort and tendency do not have to be at odds. This suede wallabee style shoe is all you need for marathon work days. With soft suede design and stitched details, it is perfect to combine with jeans",
    "stock":4,
    "price":30,
    "category":{"id":1,"name": "shoes"}
    }'
### Microservice Customer

  $ SPRING_PROFILES_ACTIVE=default gradle bootRun
  $ SPRING_PROFILES_ACTIVE=primary gradle bootRun
  
  
GET

    curl -X GET http://localhost:8092/customers    -H 'Accept: application/json' | jq '.'

POST

    curl --request POST 'localhost:8092/customers' \
    --header 'Content-Type: application/json' \
    --data-raw '
        {
            "numberID":"40408083",
            "firstName": "Luis",
            "lastName": "rodriguez",
            "email": "profesor@digitallab.academy",
            "photoUrl": "",
            "region": {
                "id": 1
            }
        }
    '

### Microservice Shopping
GET
    curl -X GET http://localhost:8093/invoices/1 -H 'Accept: application/json' | jq '.'

POST

    curl  --request POST 'localhost:8093/invoices' \
    --header 'Content-Type: application/json' \
    --data-raw '{

        "numberInvoice": "002",
        "description": "invoice store",
        "customerId": 1,
        "items": [
            {
                "quantity": 1,
                "priceItem": 178.89,
                "productId": 1
            },
    
            {
                "quantity": 2,
                "priceItem": 40.06,
                "productId": 3
            }
        ]
    }'
### Config Service

https://cloud.spring.io/spring-cloud-config/reference/html/

    $ curl http://root:s3cr3t@localhost:8081/product-service/default | jq "."
    $ curl http://root:s3cr3t@localhost:8081/customer-service/default | jq "."
    $ curl http://root:s3cr3t@localhost:8081/shopping-service/default | jq "."
