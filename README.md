# 🏦Banking Application

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
# 🚀 How to Run the Project

Follow the steps below to run the **Spring Boot backend** and **React frontend** locally.

---

## 📋 Prerequisites

Make sure the following are installed on your system:

* ☕ Java 25+
* 📦 Maven
* 🟢 Node.js 18+
* 📦 npm
* 🔧 Git

Check the installed versions:

```bash
java -version
mvn -version
node -version
npm -version
```

---

## 📥 1. Clone the Repository

```bash
git clone https://github.com/PrathmeshMutke/Prism-Byte.git
```

Navigate to the project directory:

```bash
cd Prism-Byte
```

---

# ⚙️ 2. Run the Backend

The backend is built using **Spring Boot** and runs on port `8080`.

### Windows

Open a terminal in the project root and run:

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

After the application starts successfully, the backend API will be available at:

```text
http://localhost:8080/api
```

### Backend Health Check

You can verify that the backend is running by opening:

```text
http://localhost:8080/api
```

---

# ⚛️ 3. Run the Frontend

Open a **new terminal** while keeping the backend running.

Navigate to the frontend directory:

```bash
cd frontend
```

Install the required dependencies:

```bash
npm install
```

Start the React development server:

```bash
npm run dev
```

The frontend will be available at:

```text
http://localhost:5173
```

---

# 🔗 4. Frontend & Backend Connection

The React frontend communicates with the Spring Boot backend through REST APIs.

### Backend API

```text
http://localhost:8080/api
```

### Frontend

```text
http://localhost:5173
```

If the frontend requires an API URL configuration, create:

```text
frontend/.env
```

and add:

```env
VITE_API_BASE_URL=http://localhost:8080/api
```

---

# 🖥️ 5. Run Both Together

You need **two terminals**.

### Terminal 1 — Backend

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

### Terminal 2 — Frontend

From the project root:

```bash
cd frontend
npm install
npm run dev
```

Once both applications are running:

| Application    | URL                                       |
| -------------- | ----------------------------------------- |
| ⚛️ Frontend    | http://localhost:5173                     |
| 🌱 Backend API | http://localhost:8080/api                 |
| 📚 Swagger UI  | http://localhost:8080/api/swagger-ui.html |
| 🗄️ H2 Console | http://localhost:8080/api/h2-console      |

---

# 👤 Demo Login Credentials

Use the following seeded accounts to test the application:

| Role           | Email                    | Password         |
| -------------- | ------------------------ | ---------------- |
| 🛡️ Admin      | `admin@prismbyte.com`    | `Admin@12345`    |
| 👨‍💼 Employee | `employee@prismbyte.com` | `Employee@12345` |
| 👤 Customer    | `customer@prismbyte.com` | `Customer@12345` |

> ⚠️ These credentials are intended for local development and testing only.

---

# 🛑 Stopping the Application

To stop either application, press:

```text
Ctrl + C
```

in the corresponding terminal.

---

# ✅ Quick Start

If Java and Node.js are already installed:

```bash
# Clone
git clone https://github.com/PrathmeshMutke/Prism-Byte.git

# Backend
cd Prism-Byte
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Then open another terminal:

```bash
cd Prism-Byte/frontend
npm install
npm run dev
```

Open the application in your browser:

```text
http://localhost:5173
```

