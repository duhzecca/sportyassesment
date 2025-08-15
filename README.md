
---

# SportyAssignment - Aviation API Wrapper

## Setup & Run Instructions

1. **Clone the repository:**
   ```
   git clone <your-repo-url>
   cd sportyassignment
   ```

2. **Build and run:**
   ```
   mvn clean install
   mvn spring-boot:run
   ```
   The service will start on port `8080` by default.

3. **API Usage:**
    - Fetch airport details by ICAO code:
      ```
      GET /airports?apt={ICAO}
      ```
    - Example:
      ```
      curl "http://localhost:8080/airports?apt=KAVL"
      ```
    - Swagger documentation can be access on:  
      ```
      http://localhost:8080/swagger-ui/index.html

## Running Tests

- **Unit and integration tests:**
  ```
  mvn test
  ```
- Unit tests are in `src/test/java/com/example/sportyassignment/unit/`
- Integration tests are in `src/test/java/com/example/sportyassignment/integration/`

## Assumptions & Architecture Decisions

- **Assumptions:**
    - Only ICAO code lookup is in scope.
    - The third-party API (`aviationapi.com`) is accessible but may be unstable.
    - No frontend or user management required.

- **Architecture:**
    - Layered structure: Controller → Service → API Client.
    - Service and client are decoupled for extensibility (easy to swap providers).
    - Stateless design for scalability.
    - Observability: basic logging and error transparency included.

- **Error Handling:**
    - Custom exceptions for common HTTP errors:
        - `AirportNotFoundException` (404)
        - `BadRequestException` (400)
        - `InternalServerErrorException` (500)
        - `ServiceUnavailableException` (503)
    - Graceful error responses via `AirportExceptionHandler`.
    - Upstream API failures handled with retry/circuit breaker (Resilience4j).
    - Unexpected errors return a generic error message.

---
