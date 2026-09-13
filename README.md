PrismByte Banking Application

A production-style full-stack banking application built with Java, Spring Boot, React, and JWT-based security. The platform provides role-based experiences for customers, employees, and administrators, with support for account management, transactions, administration, validation, logging, and API documentation.

🚀 Overview

PrismByte is designed to demonstrate how a modern banking platform can be structured using a layered Spring Boot backend and a React frontend.

Key Highlights

Secure JWT authentication with refresh-token support

Role-based authorization for ADMIN, EMPLOYEE, and CUSTOMER

Savings and current account management

Deposits, withdrawals, and account-to-account transfers

Transaction history and balance tracking

Account freeze/unfreeze functionality

Admin dashboard and user/transaction management

React + Vite + Tailwind CSS frontend

Swagger/OpenAPI API documentation

Global exception handling and request validation

File-based application logging

Seeded demo users for quick testing

🛠️ Tech Stack

Backend

Technology

Purpose

Java 25

Backend development

Spring Boot 4

REST API and application framework

Spring Security

Authentication and authorization

JWT

Stateless authentication

Spring Data JPA

Data persistence

Hibernate

ORM

Maven

Dependency and build management

H2

Local development database

MySQL / PostgreSQL

Production-ready database options

Swagger / OpenAPI

API documentation

Frontend

Technology

Purpose

React

User interface

Vite

Frontend build tooling

Tailwind CSS

Styling

React Router

Client-side routing

Axios

API communication

✨ Features

🔐 Authentication & User Management

User registration

Login with JWT authentication

Access-token and refresh-token flow

Role-based access control

Customer, employee, and admin roles

Profile management

Secure protected routes

🏦 Account Management

Create savings accounts

Create current accounts

Multiple accounts per user

Real-time balance tracking

Account status management

Freeze and unfreeze accounts

💸 Banking Transactions

Deposit money

Withdraw money

Transfer money between accounts

Transaction history

Transaction status tracking

Balance updates after successful transactions

👨‍💼 Admin Operations

View all users

View all transactions

Manage account status

Freeze and unfreeze accounts

Role-based administrative access

🧑‍💻 Developer Experience

Layered backend architecture

DTO-based API design

Global exception handling

Input validation

Swagger/OpenAPI documentation

File-based logging

Seeded demo data

Backend tests

Production configuration notes

🏗️ Project Structure

Prism-Byte/
│
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── ...
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/prismbyte/banking_app/
│       │       ├── bootstrap/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── dto/
│       │       ├── entity/
│       │       ├── exception/
│       │       ├── repository/
│       │       ├── security/
│       │       └── service/
│       │
│       └── resources/
│
├── logs/
├── pom.xml
└── README.md

Architecture

The backend follows a layered architecture:

React Frontend
      │
      ▼
   REST API
      │
      ▼
 Controllers
      │
      ▼
    DTOs
      │
      ▼
   Services
      │
      ▼
 Repositories
      │
      ▼
 JPA / Hibernate
      │
      ▼
 H2 / MySQL / PostgreSQL

Security is handled through Spring Security + JWT, while centralized exception handling and validation help keep API behavior consistent.

⚙️ Getting Started

Prerequisites

Make sure the following are installed:

Java 25

Maven

Node.js and npm

Git

Check your installations:

java -version
mvn -version
node -version
npm -version

🔧 Backend Setup

Clone the repository:

git clone https://github.com/PrathmeshMutke/Prism-Byte.git
cd Prism-Byte

Run the backend using Maven Wrapper.

Windows PowerShell

.\mvnw.cmd spring-boot:run

Linux / macOS

./mvnw spring-boot:run

The backend will be available at:

http://localhost:8080/api

🎨 Frontend Setup

Open a new terminal and move into the frontend directory:

cd frontend

Install dependencies:

npm install

Start the development server:

npm run dev

The frontend will be available at:

http://localhost:5173

The frontend expects the backend API at:

http://localhost:8080/api

If required, create a frontend/.env file:

VITE_API_BASE_URL=http://localhost:8080/api

👤 Demo Credentials

The application includes seeded demo users for testing.

Role

Email

Password

Admin

admin@prismbyte.com

Admin@12345

Employee

employee@prismbyte.com

Employee@12345

Customer

customer@prismbyte.com

Customer@12345

Security: These credentials are intended only for local/demo use. Replace them before deploying the application.

📚 API Documentation

Once the backend is running, Swagger UI is available at:

http://localhost:8080/api/swagger-ui.html

OpenAPI JSON:

http://localhost:8080/api/v3/api-docs

Main API Areas

/api/auth/*
/api/users/me
/api/accounts/*
/api/transactions/*
/api/admin/*

🗄️ Database

H2 is configured for local development.

H2 Console:

http://localhost:8080/api/h2-console

The application can also be configured to use:

MySQL

PostgreSQL

For a production deployment, use a managed relational database and configure credentials through environment variables or a secure configuration mechanism.

📝 Logging

Application logs are written to:

logs/banking-app.log

File-based logging can be useful for debugging authentication, API requests, transaction workflows, and application errors during development.

🧪 Testing & Verification

Backend Tests

Run:

./mvnw test

Windows PowerShell:

.\mvnw.cmd test

Frontend Production Build

From the frontend directory:

npm run build

🔒 Security

The application implements:

JWT-based authentication

Role-based authorization

Protected API endpoints

Password-based authentication

Refresh-token flow

Input validation

Global exception handling

Production Security Checklist

Before production deployment:

Replace the default JWT secret

Replace seeded demo credentials

Store secrets outside source control

Configure MySQL or PostgreSQL

Enable HTTPS

Configure appropriate CORS policies

Add database migrations using Flyway or Liquibase

Add integration and end-to-end tests

Review logging to ensure sensitive information is not recorded

📸 Screenshots

You can add screenshots of the application here:

docs/
├── login.png
├── customer-dashboard.png
├── accounts.png
├── transactions.png
└── admin-console.png

Example:

![Login Page](docs/login.png)
![Customer Dashboard](docs/customer-dashboard.png)
![Transactions](docs/transactions.png)
![Admin Console](docs/admin-console.png)

🗺️ Future Improvements

Potential enhancements for future versions:

Email notifications

OTP / MFA authentication

Scheduled bank transfers

Beneficiary management

Transaction export to PDF/CSV

Advanced admin analytics

Audit trail for sensitive operations

Redis-based caching

Docker support

CI/CD pipeline

Flyway/Liquibase database migrations

Integration and end-to-end testing

Cloud deployment

🎯 Learning Outcomes

This project demonstrates practical experience with:

Building RESTful APIs using Spring Boot

Designing layered backend architecture

Implementing Spring Security and JWT authentication

Role-based access control

JPA/Hibernate entity relationships

Transaction processing

React frontend development

API integration using Axios

Client-side routing

Tailwind CSS

Validation and exception handling

Swagger/OpenAPI

Logging and application debugging

Full-stack application development

👨‍💻 Project

PrismByte Banking Application

Built with:

Java • Spring Boot • Spring Security • JWT • JPA/Hibernate • React • Vite • Tailwind CSS • MySQL/PostgreSQL

📄 License

This project is intended for learning, development, and portfolio purposes.
