# Bookstore API with JWT Authentication and Authorization

This is a Java and Spring Boot-based RESTful API for managing a bookstore's inventory and user authentication/authorization using JWT (JSON Web Tokens). This API allows you to perform various operations related to books and users within a bookstore system.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Authentication](#authentication)
  - [Endpoints](#endpoints)
- [Authentication and Authorization](#authentication-and-authorization)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## Features
- **Book Management:**
  - Create, Read, Update, and Delete books.
  - List all books or search for specific ones.
- **User Management:**
  - User registration.
  - User login with JWT authentication.
  - User role-based authorization (Admin and User roles).
- **JWT Authentication:**
  - Secured with JSON Web Tokens for user authentication.
  - Token expiration and refresh mechanisms.
- **Swagger Documentation:**
  - Interactive API documentation using Swagger UI.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 17
- Apache Maven
- PostgreSQL database (or other relational database of your choice)

### Installation
1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/bookstore-springboot-api.git
   cd bookstore-springboot-api
   ```

2. Configure your database settings in `src/main/resources/application.properties`.

3. Build and run the application:

   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`.

## Usage

### Authentication
To use the API, you need to authenticate using JWT. Here are the steps to obtain a token:

1. **Registration:**
   - Send a POST request to `/auth/register` with the user's details (login, password, role).
   - This will create a new user account.

2. **Login:**
   - Send a POST request to `/auth/login` with the user's credentials (login and password).
   - This will return a JWT token.

3. **Authorization:**
   - Include the JWT token in the `Authorization` header of your requests as `Bearer <token>`.

### Endpoints

#### Books Endpoints
- **GET** `/books`: Get a list of all books.
- **GET** `/books/{name}`: Get a list of books by title.
- **GET** `/books/{id}`: Get details of a specific book by ID.
- **POST** `/books`: Create a new book (Admin only). Obs.: If a user includes a book with the same title and author of an existing book, the quantity of the existing book will be updated.
- **PUT** `/books/{id}`: Update an existing book (Admin only).
- **DELETE** `/books/{id}`: Delete a book by ID (Admin only).

#### Users Endpoints
- **GET** `/auth`: Get a list of all users (Admin only).
- **POST** `/api/register`: Register a new user.
- **POST** `/api/login`: Authenticate and obtain a JWT token.

## Authentication and Authorization

- **User Roles:**
  - Admin: Can perform all CRUD operations on books and view all users.
  - User: Can view books.

- **Authorization:**
  - Admin endpoints require a user with the "Admin" role.
  - User endpoints require a valid JWT token.

## Examples

### Register a User
```http
POST /auth/register
Content-Type: application/json

{
  "login": "newuser",
  "password": "password123",
  "role": "ADMIN",
}
```

### Login and Get JWT Token
```http
POST /auth/login
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123"
}
```

### Create a New Book (Admin)
```http
POST /books
Authorization: Bearer <JWT Token>
Content-Type: application/json

{
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "price": 11.85,
  "quantity": 12
}
```

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow the [Contributing Guidelines](CONTRIBUTING.md).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

