# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=MsClientsApplicationTests
```

## Stack

- **Spring Boot 4.0.6** with Java 21
- **Spring Data JPA** + **MySQL** (`db_automotora_vm` on localhost:3306)
- **Flyway** for schema migrations (scripts in `src/main/resources/db/migration/`)
- **Spring Cloud OpenFeign** for inter-service HTTP calls
- **Lombok** for boilerplate reduction

## Architecture

Layered microservice following Controller → Service → Repository:

- `controller/` — REST endpoints under `/api/clients`
- `service/` — business logic, uses Slf4j for logging
- `repository/` — Spring Data JPA interfaces extending `JpaRepository`
- `model/` — JPA entities mapped to database tables
- `dto/` — data transfer objects with Bean Validation (`@NotBlank`, `@Email`, etc.)
- `mapper/` — manual bidirectional mapping between `Model` ↔ `DTO` (no MapStruct)

The `ClientMapper` is a plain `@Component` with `toDTO` and `toEntity` methods — keep this pattern for any new mappers.

## Database

MySQL connection is configured in `application.properties` with `ddl-auto=update`. Flyway migrations are the source of truth for schema creation; the SQL migration files use versioned naming (`V{major}.{minor}.{patch}__description.sql`).

## Context

This is the `ms-clients` microservice within a larger `proyecto-automotora` multi-module project. A sibling `ms-sales` service exists at the parent directory level. OpenFeign is included as a dependency for future cross-service calls.
