# Microservice E-Commerce Platform

A production-style e-commerce backend built with **Spring Boot 3.5 + Java 21**, decomposed into three independently deployable microservices — Customer, Product, and Order — communicating over REST with service discovery via Spring Cloud Netflix Eureka.

---

## Architecture Overview

```
                        ┌─────────────────────────────────┐
                        │       Eureka Discovery Server    │
                        │         (Spring Cloud)           │
                        └────────────┬────────────────────┘
                                     │ service registry
               ┌─────────────────────┼─────────────────────┐
               ▼                     ▼                       ▼
  ┌────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
  │   CustomerService  │  │   ProductService     │  │    OrderService      │
  │   :9001            │  │   :9002              │  │    :9003             │
  │                    │  │                      │  │                      │
  │  • CRUD customers  │  │  • CRUD products     │  │  • Place orders      │
  │  • Mobile lookup   │  │  • Price range       │  │  • Aggregate VO      │
  │  • Bean Validation │  │    filter            │  │  • RestTemplate      │
  │  • Swagger UI      │  │  • Swagger UI        │  │    inter-svc calls   │
  └────────┬───────────┘  └──────────┬──────────┘  └──────────┬──────────┘
           │                         │                          │
           └──────────────┬──────────┘                         │
                          │         MySQL 8 (shared DB)         │
                          └─────────────────────────────────────┘
                                  database: `shopping`
```

**Inter-service communication:** OrderService calls CustomerService (`localhost:9001`) and ProductService (`localhost:9002`) via `RestTemplate` to assemble a rich `OrderVO` response — customer details + product details + order metadata — in a single API response.

---

## Services

### CustomerService — port `9001`

Manages customer accounts with input validation enforced at the entity level.

**Domain model:**

| Field      | Type     | Constraint                        |
|------------|----------|-----------------------------------|
| `custId`   | `int`    | PK, must be ≥ 100                 |
| `custName` | `String` | min 5 characters                  |
| `mobile`   | `String` | unique, exactly 10 digits         |

**Endpoints:**

| Method | Path                              | Description                      |
|--------|-----------------------------------|----------------------------------|
| `POST` | `/api/v1/customers`               | Register a new customer          |
| `GET`  | `/api/v1/customers`               | List all customers               |
| `GET`  | `/api/v1/customers/{id}`          | Fetch customer by ID             |
| `GET`  | `/api/v1/customers/mobile/{mob}`  | Fetch customer by mobile number  |

**Stack:** Spring Boot 3.5, Spring Data JPA, Bean Validation, springdoc-openapi (Swagger), MySQL, Eureka Client

---

### ProductService — port `9002`

Manages the product catalog with price-range filtering support.

**Domain model:**

| Field   | Type     | Description       |
|---------|----------|-------------------|
| `code`  | `int`    | PK (product code) |
| `name`  | `String` | Product name      |
| `price` | `double` | Unit price        |

**Endpoints:**

| Method | Path                                              | Description                        |
|--------|---------------------------------------------------|------------------------------------|
| `POST` | `/api/v1/products`                                | Add a new product                  |
| `GET`  | `/api/v1/products`                                | List all products                  |
| `GET`  | `/api/v1/products/{code}`                         | Fetch product by code              |
| `GET`  | `/api/v1/products/price?min={min}&max={max}`      | Filter products by price range     |

**Stack:** Spring Boot 3.5, Spring Data JPA, springdoc-openapi (Swagger), MySQL, Eureka Client

---

### OrderService — port `9003`

Places orders and aggregates a full `OrderVO` response by calling CustomerService and ProductService at runtime.

**Domain model (`Order` entity):**

| Field       | Type        | Description             |
|-------------|-------------|-------------------------|
| `orderNum`  | `int`       | PK (auto-generated)     |
| `orderDate` | `LocalDate` | Date of order           |
| `amt`       | `double`    | Order amount            |
| `custId`    | `int`       | FK ref to customer      |
| `code`      | `int`       | FK ref to product       |

**Aggregate response (`OrderVO`):**

```json
{
  "orderId": 1,
  "ordDate": "2025-06-10",
  "amount": 1499.00,
  "customer": {
    "custID": 101,
    "custName": "Janhavi",
    "mobile": "9876543210"
  },
  "item": {
    "code": 201,
    "name": "Wireless Headphones",
    "price": 1499.00
  }
}
```

**Endpoints:**

| Method | Path                               | Description                                    |
|--------|------------------------------------|------------------------------------------------|
| `POST` | `/api/v1/orders`                   | Place an order (validates customer + product)  |
| `GET`  | `/api/v1/orders/{ordNo}`           | Fetch order with full customer & product info  |
| `GET`  | `/api/v1/orders/customer/{custId}` | Fetch all orders for a customer                |

**Stack:** Spring Boot 3.5, Spring Data JPA, RestTemplate, springdoc-openapi (Swagger), MySQL, Eureka Client

---

## Tech Stack

| Layer               | Technology                                      |
|---------------------|-------------------------------------------------|
| Language            | Java 21                                         |
| Framework           | Spring Boot 3.5.14                              |
| Service Discovery   | Spring Cloud 2025.0.2 + Netflix Eureka          |
| Persistence         | Spring Data JPA + Hibernate (MySQL8Dialect)     |
| Database            | MySQL 8                                         |
| Validation          | Jakarta Bean Validation (`@Min`, `@Size`)       |
| API Docs            | springdoc-openapi 2.8 (Swagger UI)              |
| Build               | Maven                                           |

---

## Project Structure

```
Microservice-E-comm/
├── CustomerService/
│   └── src/main/java/com/day8/CustomerService/
│       ├── entity/         # Customer.java
│       ├── repo/           # CustomerRepo (JpaRepository)
│       ├── service/        # CustomerService interface + impl
│       ├── exception/      # InvalidCustomerException
│       └── vo/             # CustomerVO (for inter-service use)
│
├── ProductService/
│   └── src/main/java/com/day8/ProductService/
│       ├── entity/         # Product.java
│       ├── repo/           # ProductRepo (JpaRepository)
│       ├── service/        # ProductService interface + impl
│       ├── exception/      # InvalidProductException
│       └── vo/             # ProductVO (for inter-service use)
│
└── OrderService/
    └── src/main/java/com/day8/OrderService/
        ├── entity/         # Order.java
        ├── repo/           # OrderRepo (JPQL custom query)
        ├── service/        # OrderService interface + impl
        └── vo/             # OrderVO (aggregated response)
```

---

## Getting Started

### Prerequisites

- Java 21
- Maven 3.9+
- MySQL 8 running locally

### 1. Database Setup

```sql
CREATE DATABASE shopping;
```

Each service auto-creates its tables on startup (`ddl-auto=update`). No manual schema needed.

### 2. Configure Credentials

Update `application.properties` in each service before running:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

> ⚠️ **Do not commit real credentials.** Use environment variables or `application-local.properties` (gitignored) in production setups.

### 3. Run Services

Start each service in a separate terminal. Order matters — CustomerService and ProductService should be up before OrderService.

```bash
# Terminal 1
cd CustomerService
mvn spring-boot:run

# Terminal 2
cd ProductService
mvn spring-boot:run

# Terminal 3
cd OrderService
mvn spring-boot:run
```

### 4. API Documentation (Swagger UI)

Once running, interactive API docs are available at:

| Service         | Swagger URL                                |
|-----------------|--------------------------------------------|
| CustomerService | http://localhost:9001/swagger-ui/index.html |
| ProductService  | http://localhost:9002/swagger-ui/index.html |
| OrderService    | http://localhost:9003/swagger-ui/index.html |

---

## Sample Requests

**Register a customer:**
```http
POST http://localhost:9001/api/v1/customers
Content-Type: application/json

{
  "custId": 101,
  "custName": "Janhavi",
  "mobile": "9876543210"
}
```

**Add a product:**
```http
POST http://localhost:9002/api/v1/products
Content-Type: application/json

{
  "code": 201,
  "name": "Wireless Headphones",
  "price": 1499.00
}
```

**Place an order:**
```http
POST http://localhost:9003/api/v1/orders
Content-Type: application/json

{
  "custId": 101,
  "code": 201,
  "orderDate": "2025-06-10",
  "amt": 1499.00
}
```

**Fetch order (aggregated response):**
```http
GET http://localhost:9003/api/v1/orders/1
```

---

## Design Decisions

**VO pattern for inter-service data** — Each service exposes its own `CustomerVO` / `ProductVO` so OrderService can consume them without coupling to the other services' JPA entities directly.

**Bean Validation at entity level** — `@Min`, `@Size`, and `@Column(unique=true)` constraints enforce business rules (customer IDs ≥ 100, mobile numbers 10 digits) at the persistence layer.

**RestTemplate for service calls** — OrderService uses `RestTemplate` with configurable base URLs (`customer-service.url`, `product-service.url` in `application.properties`) to call sibling services, keeping configuration explicit and easy to override.

**Custom JPQL query** — `OrderRepo` uses `@Query` to find all orders by `custId` without requiring a bidirectional JPA relationship, keeping the entity model lean.

---

## Roadmap

- [ ] Add Eureka Discovery Server module for dynamic service registration
- [ ] Replace `RestTemplate` with `OpenFeign` for declarative inter-service calls
- [ ] Introduce API Gateway (Spring Cloud Gateway) as single entry point
- [ ] Add circuit breaker (Resilience4j) for fault tolerance
- [ ] Dockerize all services with `docker-compose`
- [ ] Move credentials to environment variables / Spring Cloud Config

---

## Author

**Janhavi Vaidya**  
B.Tech CSE @ RCOEM Nagpur  
[GitHub](https://github.com/Janhavi1214)
