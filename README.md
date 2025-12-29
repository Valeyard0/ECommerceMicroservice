Spring Boot Microservices Project

A full-featured Spring Boot Microservices project built with modern cloud-native technologies.


About The Project
This project is a Spring Boot Microservices Architecture that provides authentication, authorization.
Users can register and login via Auth Service.  
All requests are routed through API Gateway using JWT Bearer Token authentication.
The system supports role-based authorization with `ADMIN` and `USER` roles.

## Microservices
- Eureka Server
- API Gateway
- Auth Service
- User Service
- Product Service
- Order Service

---

## Technologies Used
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Cloud Gateway
- Netflix Eureka
- H2
- Kafka
- Docker
- Lombok
- ModelMapper


## Security
- Stateless JWT-based authentication
- Role-based authorization (`ROLE_ADMIN`, `ROLE_USER`)
- Gateway-level token validation



## User Service – REST APIs

| Method | Endpoint | Description | Request Body | Authentication |
|------|---------|-------------|--------------|----------------|
| POST | `/api/v1/users/createUser` | Create new user | RegisterRequest | Public |
| GET | `/api/v1/users` | Get all users | – | Public |
| GET | `/api/v1/users/{userId}` | Get user by id | – | Public |
| GET | `/api/v1/users/username/{username}` | Get user by username | – | Public |
| GET | `/api/v1/users/keyword/{keyword}` | Search users by keyword | – | Public |
| PUT | `/api/v1/users/userId/{userId}` | Update user | UserDTO | Authenticated (Owner / ADMIN) |
| DELETE | `/api/v1/users/{userId}` | Delete user | – | Authenticated (Owner / ADMIN) |



## Category Service(INSIDE OF PRODUCTSERVICE) – REST APIs

| Method | Endpoint | Description | Request Body | Authentication |
|------|---------|-------------|--------------|----------------|
| POST | `/api/v1/categories` | Create category | CategoryDTO | ADMIN |
| GET | `/api/v1/categories` | Get all categories (paged & sorted) | – | Public |
| GET | `/api/v1/categories/categoryId/{categoryId}` | Get category by id | – | Public |
| GET | `/api/v1/categories/categoryName/{categoryName}` | Get categories by name (paged) | – | Public |
| PUT | `/api/v1/categories/categoryId/{categoryId}` | Update category | CategoryDTO | ADMIN |
| DELETE | `/api/v1/categories/categoryId/{categoryId}` | Delete category | – | ADMIN |



## Product Service – REST APIs

| Method | Endpoint | Description | Request Body | Authentication |
|------|---------|-------------|--------------|----------------|
| POST | `/api/v1/categories/{categoryId}/products` | Create product by category | ProductDTO | ADMIN |
| GET | `/api/v1/products` | Get all products (paged & sorted) | – | Public |
| GET | `/api/v1/products/{productId}` | Get product by id | – | Public |
| PUT | `/api/v1/products/{productId}` | Update product | ProductDTO | ADMIN |
| PUT | `/api/v1/products/{productId}/image` | Update product image | MultipartFile | ADMIN |
| DELETE | `/api/v1/products/{productId}` | Delete product | – | ADMIN |


## Cart Service – REST APIs

| Method | Endpoint | Description | Request Body | Authentication |
|------|---------|-------------|--------------|----------------|
| POST | `/api/v1/cart` | Add product to cart / Create cart | CartRequest | Authenticated (JWT) |
| PUT | `/api/v1/cart` | Update product quantity in cart | CartRequest | Authenticated (JWT) |
| DELETE | `/api/v1/cart` | Remove product from cart | CartRequest | Authenticated (JWT) |
| GET | `/api/v1/cart` | Get logged-in user's cart | – | Authenticated (JWT) |
| GET | `/api/v1/carts` | Get all carts | – | Authenticated (JWT) |


## Order Service – REST APIs

| Method | Endpoint | Description | Request Body | Authentication |
|------|---------|-------------|--------------|----------------|
| POST | `/api/v1/order` | Create order from cart | – | Authenticated (JWT) |


##  Docker Services & Ports

| Service Name | Description            | Container Port | Host Port |
|--------------|------------------------|----------------|-----------|
| Zookeeper    | Kafka Coordination     | 2181           | 2181      |
| Kafka        | Message Broker         | 9092           | 9092      |


### Example JSON
```json
{
  "id": 1,
  "username": "string",
  "email": "string",
  "password": "string",
  "roles": [
    {
      "id": 1,
      "name": "ROLE_USER"
    }
  ],
  "addresses": [
    {
      "city": "string",
      "district": "string",
      "street": "string",
      "postalCode": "string"
    }
  ]
}
 Register Request
Example JSON

{
  "username": "string",
  "password": "string",
  "email": "string",
  "addresses": [
    {
      "city": "string",
      "district": "string",
      "street": "string",
      "postalCode": "string"
    }
  ]
}
 Product
Example JSON

{
  "id": 1,
  "name": "string",
  "price": 0,
  "quantity": 0,
  "description": "string",
  "image": "string"
}
Cart Request
Example JSON
{
  "productId": 1,
  "quantity": 1
}

Category
{
  "id": 1,
  "name": "ELECTRONICS"
}

Category Enum
[
  "FURNITURE",
  "ELECTRONICS",
  "SPORTS"
]
