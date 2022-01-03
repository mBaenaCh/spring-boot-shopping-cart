# spring-boot-shopping-cart

Springboot project to understand the basics of a Domain Driven Design by implementing a Shopping cart REST API

## Project structure

```text
Marketplace
└───configuration       configuration Beans for the project
│   └───jackson         parsing classes to serialize and deserialize domain classes with Jackson
│   
└───controllers         REST controller to handle requests to the API resources
│   
└───domain              model of domain classes
│   
└───model               intermediary classes for Inputs and Outputs used in the controller
│   
└───services            class to handle the rules of the business methods
│   
└───repository          intermediary class to handle the database connections
```

## REST API

Can be tested in POSTMAN with the given collection .JSON file

In summary, the services that we can found are the following:

### Products services

| Method     | Endpoint | Description     |
| :----:        | :----:   |:---- |
| GET      | /api/shopping-cart/products       | Get all products   |
| GET   | /api/shopping-cart/products/:id        | Get product by id      |
| POST   | /api/shopping-cart/products/        | Create product      |
| PUT   | /api/shopping-cart/products/:id        | Update product by id      |
| DELETE   | /api/shopping-cart/products/:id        | Delete product by id      |

### Shopping cart services

| Method     | Endpoint | Description     |
| :----:        | :----:   |:---- |
| POST      | /api/shopping-cart       | Create shopping cart   |
| GET   | /api/shopping-cart/:id        | Get shopping cart by id      |
| PUT   | /api/shopping-cart/:scId/add-product/:pId        | Add product by its id (pId) to shopping cart by its id (scId)     |
| PUT   | /api/shopping-cart/:scId/remove-product/:pId        | Remove product by its id (pId) from shopping cart by its id (scId)      |
| PUT   | /api/shopping-cart/:scId/increase-quantity/:pId        | Increase quantity of a product by its Id (pId) in a shopping cart      |
| PUT   | /api/shopping-cart/:scId/decrease-quantity/:pId        | Decrease quantity of a product by its Id (pId) in a shopping cart      |
