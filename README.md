# LabSeq Service

A RESTful web service built with Quarkus to calculate the `labseq` mathematical sequence.

## Features

- **LabSeq Calculation API**: `GET /labseq/{n}` Calculates sequence values using caching for O(1) retrieval of previously computed indexes, and an iterative approach allowing O(N) linear time calculation.
- **Glassmorphism UI**: Access the base path (`http://localhost:8080/`) for a visually appealing Vue.js/Vanilla Javascript frontend to invoke the API.
- **OpenAPI/Swagger**: Accessible at `http://localhost:8080/q/swagger-ui`.

## Sequence Definition
```
n = 0 => l(0) = 0
n = 1 => l(1) = 1
n = 2 => l(2) = 0
n = 3 => l(3) = 1
n > 3 => l(n) = l(n-4) + l(n-3)
```

## How to Run

### Using Docker (Recommended)

Make sure you have Docker and Docker Compose installed.

1. Build and start the service:
```bash
docker-compose up -d --build
```

2. Access the Application:
   - UI: `http://localhost:8080/`
   - Swagger Documentation: `http://localhost:8080/q/swagger-ui`
   - Direct API Endpoint: `http://localhost:8080/labseq/10`

### Locally using Maven (Java 21 required)

1. Start in dev mode:
```bash
mvn quarkus:dev
```

## Performance
The application uses `ConcurrentHashMap` caching to memoize previously verified values. The $l(100000)$ algorithm runs linearly (if not cached) to prevent `StackOverflowError` and handles large number arithmetic in well under 10 seconds.
