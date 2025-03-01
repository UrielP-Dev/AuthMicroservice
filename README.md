# Auth Service

This microservice handles user authentication and authorization using JWT.

## 🚀 Technologies
- Java 17
- Spring Boot
- Spring Security
- JWT
- MongoDB (for user data)

## 📂 Endpoints
| Method | Endpoint         | Description                 |
|--------|-----------------|-----------------------------|
| POST   | `/auth/register` | Registers a new user       |
| POST   | `/auth/login`    | Logs in and generates a JWT |
| GET    | `/auth/me`       | Retrieves user information |

## 📦 Installation
1. Clone the repository.
2. Run: `mvn spring-boot:run`
