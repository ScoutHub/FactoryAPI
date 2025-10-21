# Factory API - Client & Contract Management

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
- **Config**: Middlewares and others configuration files
- **Utils**: Utilities like JWT

### Technical Decisions
- **Single Table Inheritance**: Optimizes query performance for Client polymorphism (Person/Company)
- **UUID Primary Keys**: Enhanced security, prevents enumeration attacks
- **Global Exception Handling**: Consistent API error responses
- **Bcrypt** password Hashing for high security
- **Header Authorization with Bearer** for /api/** routes

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

jwt.secret=myjwtsecret
jwt.expiration=3600000

spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

Or with Docker :
```bash
docker-compose up -d
```

Don't forget to configure `.env` file and put it into `.docker/` directory. Here example file : 
```bash
# Database Configuration
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_PORT=5432

# Application Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/db
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false

JWT_SECRET=
JWT_EXPIRATION=3600000

SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration

# Server Configuration
SERVER_PORT=8080
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

## Documentation

Want to see routes documentation ? Go to http://localhost:8080/swagger-ui/index.html

<img width="1917" height="968" alt="Capture d’écran 2025-10-15 à 11 34 24" src="https://github.com/user-attachments/assets/bf75f9ff-e8ee-4578-9af0-f874649f9684" />


## Project Structure
```
src/main/java/com/vaudoise/factory
├── FactoryApplication.java
├── config
│   └── JwtAuthenticationFilter.java
├── controller
│   ├── AuthController.java
│   ├── ClientController.java
│   └── ContractController.java
├── dto
│   ├── request
│   │   ├── ClientUpdateRequestDto.java
│   │   ├── CompanyRequestDto.java
│   │   ├── ContractRequestDto.java
│   │   ├── ContractUpdateDto.java
│   │   ├── LoginRequestDto.java
│   │   ├── PersonRequestDto.java
│   │   └── RegisterRequestDto.java
│   └── response
│       ├── AuthResponseDto.java
│       ├── ClientResponseDto.java
│       └── ContractResponseDto.java
├── entity
│   ├── Client.java
│   ├── Company.java
│   ├── Contract.java
│   └── Person.java
├── exception
│   ├── EmailAlreadyExistsException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repository
│   ├── ClientRepository.java
│   ├── CompanyRepository.java
│   ├── ContractRepository.java
│   └── PersonRepository.java
├── service
│   ├── AuthService.java
│   ├── ClientService.java
│   └── ContractService.java
└── utils
    └── JwtUtil.java
```

## Technical Stack

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Jakarta Validation**
- **JWT Authorization**

## Future Improvements

- <del> Implement authentication & authorization </del>
- Implement caching for frequently accessed data
- Add integration tests
- <del> API documentation with Swagger/OpenAPI </del>
- <del> Add Docker environment </del>
