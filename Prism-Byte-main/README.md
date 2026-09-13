# Prismbyte Banking Application

Production-style full stack banking platform built with Spring Boot and React.

## Overview

This project includes:

- Spring Boot backend with layered architecture
- JWT authentication with role-based access
- Banking modules for users, accounts, transactions, and admin operations
- React + Vite + Tailwind frontend for customers, employees, and admins
- Swagger/OpenAPI documentation
- File-based logging for debugging

## Tech Stack

### Backend

- Java 25
- Spring Boot 4
- Spring Security
- JWT
- Spring Data JPA / Hibernate
- Maven
- H2 for local development
- MySQL / PostgreSQL ready configuration

### Frontend

- React
- Vite
- Tailwind CSS
- React Router
- Axios

## Features

### Authentication and User Management

- User registration
- Login with JWT authentication
- Refresh token flow
- Role-based access for `ADMIN`, `EMPLOYEE`, and `CUSTOMER`
- Profile management

### Account Management

- Create savings and current accounts
- Multiple accounts per user
- Balance tracking
- Account freeze and unfreeze support

### Transactions

- Deposit money
- Withdraw money
- Transfer money between accounts
- Transaction history

### Admin Features

- View all users
- View all transactions
- Freeze and unfreeze accounts

### Developer Experience

- Swagger UI
- Global exception handling
- Validation
- Application logs written to file
- Seeded demo users for quick testing

## Project Structure

```text
banking_app/
|-- frontend/                     # React + Tailwind frontend
|-- src/main/java/com/prismbyte/banking_app
|   |-- bootstrap/
|   |-- config/
|   |-- controller/
|   |-- dto/
|   |-- entity/
|   |-- exception/
|   |-- repository/
|   |-- security/
|   `-- service/
|-- src/main/resources/
`-- pom.xml
```

## Run the Backend

From the project root:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend base URL:

`http://localhost:8080/api`

## Run the Frontend

From the `frontend` folder:

```bash
npm install
npm run dev
```

Frontend URL:

`http://localhost:5173`

The frontend expects the backend at:

`http://localhost:8080/api`

If needed, create `frontend/.env`:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

## Seeded Demo Credentials

- Admin: `admin@prismbyte.com / Admin@12345`
- Employee: `employee@prismbyte.com / Employee@12345`
- Customer: `customer@prismbyte.com / Customer@12345`

## API Documentation

- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api/v3/api-docs`

## Local Database and Logs

### H2 Console

- URL: `http://localhost:8080/api/h2-console`

### Application Log File

- Log file: `logs/banking-app.log`

## Example API Areas

- `/api/auth/*`
- `/api/users/me`
- `/api/accounts/*`
- `/api/transactions/*`
- `/api/admin/*`

## Testing

Backend verification completed with:

```bash
./mvnw test
```

Frontend verification completed with:

```bash
npm run build
```

## Production Notes

- Replace the default JWT secret before production
- Replace seeded demo credentials
- Point datasource settings to MySQL or PostgreSQL
- Add Flyway or Liquibase migrations for stronger schema control
- Add integration and end-to-end tests for critical workflows

## Screenshots

You can add screenshots here later for:

- Login page
- Customer dashboard
- Accounts page
- Transactions page
- Admin console
