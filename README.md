# Securing APIs with Spring Boot

This project demonstrates how to secure APIs using Spring Boot, Spring Security, and JWT (JSON Web Token). It includes user registration, login, and role-based access control.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT
- H2 Database
- Maven

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/your-username/securing-apis.git
    cd securing-apis
    ```

2. Build the project:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### Configuration

The application uses an in-memory H2 database. You can access the H2 console at `http://localhost:8080/h2-console` with the following credentials:

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

### API Endpoints

#### Public Endpoints

- **Register**: `POST /api/register`
    - Request Body:
        ```json
        {
            "username": "your-username",
            "password": "your-password"
        }
        ```
    - Response:
        ```json
        {
            "message": "User registered successfully"
        }
        ```

- **Login**: `POST /api/login`
    - Request Body:
        ```json
        {
            "username": "your-username",
            "password": "your-password"
        }
        ```
    - Response:
        ```json
        {
            "message": "Login successful.\nUsername: your-username\nPassword: your-password\nRole: USER\nToken: your-jwt-token"
        }
        ```

#### Protected Endpoints

- **User Endpoint**: `GET /api/user` (Requires `USER` role)
- **Admin Endpoint**: `GET /api/admin` (Requires `ADMIN` role)

### Security Configuration

The security configuration is defined in `SecurityConfig.java`. It includes role-based access control and JWT token-based authentication.

### JWT Token Utility

The `JwtTokenUtil` class is responsible for generating and validating JWT tokens.

## License

This project is licensed under the MIT License.