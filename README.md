# Library Management System (Spring Boot + MongoDB + Docker)

A backend system for managing a library with authentication, role-based access, book borrowing, return policies, scheduler, and observability.

---

## Features

- JWT Authentication (Signup/Login)
- Role-based access (ADMIN / USER)
- Book Management
- Borrow & Return System
- Expiry-based auto return
- 10 PM Library Return Policy
- Actuator Metrics & Health Monitoring
- Dockerized MongoDB
- Clean layered architecture

---

## Architecture

![Architecture Diagram](./architecture.png)

```
Client (Thunder Client / Postman)
⬇
Spring Boot Application (REST APIs)
⬇
- Security Layer (JWT Filter)
- Service Layer (Business Logic)
- Scheduler (Auto return at 10 PM)
⬇
MongoDB (Docker Container)
```

---

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- MongoDB
- Docker
- JWT
- Gradle
- Spring Actuator

---

## Project Structure

```
src/main/java/com/library
│
├── config
├── controller
├── dto
├── model
├── repository
├── scheduler
├── security
├── service
│
└── LibraryManagementApplication.java
```

---

## Setup & Run

### Run with Docker

```bash
./gradlew clean bootJar
docker-compose up --build
```

---

### Run Locally

```bash
./gradlew bootRun
```

---

## API Endpoints

### Auth
- POST `/auth/signup`
- POST `/auth/login`

### Books
- GET `/books`
- POST `/books` — ADMIN only
- POST `/books/borrow/{id}` — USER only
- POST `/books/return/{id}` — USER only
- GET `/books/my` — USER only

---

## Observability

- GET `/actuator/health`
- GET `/actuator/metrics`

---

## Scheduler

- Auto returns expired books
- Enforces 10 PM return policy
- Runs using `@Scheduled`

---

## Testing

Use:
- Thunder Client (VS Code)
- Postman