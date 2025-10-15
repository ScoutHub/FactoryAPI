# Insurance API - Client & Contract Management

A RESTful API built with Java Spring Boot for managing insurance clients and their contracts.

## Architecture & Design

This application implements a classic layered monolithic architecture, strategically chosen for rapid MVP delivery and business value demonstration. As a software architect, I prioritized time-to-market and maintainability over premature complexity, following the principle of "start simple, scale smart."

### Current Architecture (V1 - Monolithic)
The layered approach provides clear separation of concerns while maintaining simplicity:
- **Controllers**: HTTP request handling and routing
- **Services**: Business logic orchestration and transaction management
- **Repositories**: Data access abstraction via Spring Data JPA
- **Entities**: Domain model with JPA mappings
- **DTOs**: API contract definitions for request/response payloads

### Technical Decisions
- **Single Table Inheritance**: Optimizes query performance for Client polymorphism (Person/Company)
- **UUID Primary Keys**: Enhanced security, prevents enumeration attacks
- **Global Exception Handling**: Consistent API error responses

### Future Evolution (V2 - Microservices)
The current design facilitates migration to microservices architecture:
- Client Service + Contract Service separation
- Event-driven communication patterns
- Kubernetes-ready containerization
- Independent scaling and deployment

This pragmatic approach balances immediate business needs with future scalability requirements.

### Database Schema
PostgreSQL database with two main tables:
- `clients`: Stores both Person and Company data (SINGLE_TABLE inheritance)
- `contracts`: Stores contract information with foreign key to clients

## Prerequisites

- Java 17 or higher
- Maven 3.5+
- PostgreSQL 14+
- Postman or similar tool for API testing

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/ScoutHub/FactoryAPI
```

### 2. Configure Database
Create a PostgreSQL database:
```sql
CREATE DATABASE factory;
```

Update `application.properties` (or `application.yml`) with your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/factory
spring.datasource.username=username
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8080`

## Testing

Run unit tests:
```bash
mvn test
```

Run with coverage:
```bash
mvn test jacoco:report
```

## Project Structure
```
src/main/java/com/vaudoise/factory/
├── controller/
│   ├── ClientController.java
│   └── ContractController.java
├── service/
│   ├── ClientService.java
│   └── ContractService.java
├── repository/
│   ├── ClientRepository.java
│   ├── PersonRepository.java
│   ├── CompanyRepository.java
│   └── ContractRepository.java
├── entity/
│   ├── Client.java
│   ├── Person.java
│   ├── Company.java
│   └── Contract.java
├── dto/
│   ├── request/
│   │   ├── PersonRequestDto.java
│   │   ├── CompanyRequestDto.java
│   │   ├── ClientUpdateRequestDto.java
│   │   ├── ContractRequestDto.java
│   │   └── ContractUpdateDto.java
│   └── response/
│       ├── ClientResponseDto.java
│       └── ContractResponseDto.java
└── exception/
    ├── ResourceNotFoundException.java
    ├── EmailAlreadyExistsException.java
    └── GlobalExceptionHandler.java
```

## Technical Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Jakarta Validation**

## Future Improvements

- Implement authentication & authorization
- Implement caching for frequently accessed data
- Add integration tests
- API documentation with Swagger/OpenAPI
- Add Docker environment