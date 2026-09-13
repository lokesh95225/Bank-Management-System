# 🏦 PrismByte Banking Application

<p align="center">
  <b>A Production-Style Full-Stack Banking Application</b>
</p>

<p align="center">
  Built with Java • Spring Boot • Spring Security • JWT • React • Vite • Tailwind CSS
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring%20Boot-4-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/Spring%20Security-JWT-green?style=for-the-badge&logo=springsecurity" />
  <img src="https://img.shields.io/badge/React-Vite-blue?style=for-the-badge&logo=react" />
  <img src="https://img.shields.io/badge/Tailwind-CSS-06B6D4?style=for-the-badge&logo=tailwindcss" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven" />
</p>

---

## 📖 About The Project

**PrismByte Banking Application** is a modern full-stack banking platform designed using a layered **Spring Boot backend** and a responsive **React frontend**.

The application provides different experiences for:

- 👤 Customers
- 👨‍💼 Employees
- 🛡️ Administrators

Users can securely authenticate, manage bank accounts, perform transactions, view transaction history, and manage account status.

The project demonstrates real-world concepts such as **JWT authentication, role-based authorization, REST APIs, JPA/Hibernate, transaction processing, validation, exception handling, and frontend-backend integration**.

---

## ✨ Features

### 🔐 Authentication & Authorization

- User registration
- Secure login using JWT
- Access token & refresh token flow
- Role-based access control
- Protected API endpoints
- Customer, Employee & Admin roles
- Profile management

### 🏦 Account Management

- Create Savings Account
- Create Current Account
- Multiple accounts per user
- Balance tracking
- Account status management
- Freeze account
- Unfreeze account

### 💸 Transactions

- Deposit money
- Withdraw money
- Transfer money between accounts
- Transaction history
- Balance updates
- Transaction validation
- Protection against transactions on frozen accounts

### 🛡️ Admin Dashboard

- View all users
- View all transactions
- View account information
- Freeze accounts
- Unfreeze accounts
- Role-based administrative operations

### 🧑‍💻 Developer Features

- Layered architecture
- DTO-based API design
- Global exception handling
- Request validation
- Swagger/OpenAPI documentation
- File-based logging
- Seeded demo users
- H2 database for development
- MySQL/PostgreSQL ready
- Backend testing

---

# 🛠️ Tech Stack

## Backend

| Technology | Purpose |
|------------|---------|
| ☕ Java 25 | Backend Development |
| 🌱 Spring Boot 4 | REST API & Application Framework |
| 🔐 Spring Security | Authentication & Authorization |
| 🎫 JWT | Secure Authentication |
| 🗃️ Spring Data JPA | Data Persistence |
| 🐘 Hibernate | ORM |
| 📦 Maven | Dependency Management |
| 🗄️ H2 | Development Database |
| 🐬 MySQL | Production Database |
| 🐘 PostgreSQL | Production Database |
| 📚 Swagger/OpenAPI | API Documentation |

## Frontend

| Technology | Purpose |
|------------|---------|
| ⚛️ React | User Interface |
| ⚡ Vite | Frontend Tooling |
| 🎨 Tailwind CSS | Styling |
| 🧭 React Router | Routing |
| 📡 Axios | API Communication |

---

# 🏗️ Application Architecture

```text
                  ┌─────────────────────┐
                  │    React Frontend   │
                  │                     │
                  │ React + Vite        │
                  │ Tailwind CSS        │
                  │ Axios               │
                  └──────────┬──────────┘
                             │
                             │ REST API
                             ▼
                  ┌─────────────────────┐
                  │   Spring Boot API   │
                  └──────────┬──────────┘
                             │
              ┌──────────────┴──────────────┐
              │                             │
              ▼                             ▼
      ┌───────────────┐             ┌───────────────┐
      │  Controllers  │             │    Security   │
      │   REST APIs   │             │ Spring + JWT  │
      └───────┬───────┘             └───────────────┘
              │
              ▼
      ┌───────────────┐
      │   Services    │
      │ Business Logic│
      └───────┬───────┘
              │
              ▼
      ┌───────────────┐
      │ Repositories  │
      │ Spring Data   │
      │     JPA       │
      └───────┬───────┘
              │
              ▼
      ┌─────────────────────┐
      │      Database       │
      │ H2 / MySQL /        │
      │ PostgreSQL           │
      └─────────────────────┘
