# Student Management REST API

A comprehensive REST API for managing students built with Spring Boot 3+, Spring Data JPA, and MySQL.

## Features

- ✅ Create, Read, Update, Delete (CRUD) operations for students
- ✅ Field validation (email format, not null, age > 18)
- ✅ Service layer architecture (Controller → Service → Repository)
- ✅ Global exception handling with proper HTTP status codes
- ✅ Pagination and sorting for GET all students
- ✅ Search functionality by name or course
- ✅ Swagger UI for API documentation
- ✅ Docker containerization support

## Technology Stack

- **Spring Boot** 3.2.0
- **Java** 17
- **Spring Data JPA**
- **MySQL** 8.0
- **SpringDoc OpenAPI** (Swagger UI)
- **Maven** for dependency management

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (if running without Docker)
- Docker and Docker Compose (optional, for containerized deployment)

## Database Setup

### Option 1: Using Docker Compose (Recommended)

The easiest way to run the application is using Docker Compose, which will set up both MySQL and the Spring Boot application:

```bash
docker-compose up
```

This will:
- Create a MySQL container on port 3306
- Create the `student_management` database automatically
- Start the Spring Boot application on port 8080

### Option 2: Manual MySQL Setup

1. Install and start MySQL on your machine
2. Create a database:
   ```sql
   CREATE DATABASE student_management;
   ```
3. Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

## Running the Application

### Using Maven

1. Clone or navigate to the project directory
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Using Docker

Build and run using Docker Compose:
```bash
docker-compose up --build
```

Or build the Docker image separately:
```bash
docker build -t student-management-api .
docker run -p 8080:8080 student-management-api
```

The application will start on `http://localhost:8080`

## API Endpoints

### Base URL
```
http://localhost:8080/api/students
```

### 1. Create Student
- **POST** `/api/students`
- **Request Body:**
  ```json
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  }
  ```
- **Response:** 201 Created
  ```json
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  }
  ```

### 2. Get All Students
- **GET** `/api/students`
- **Query Parameters:**
  - `page` (default: 0) - Page number
  - `size` (default: 10) - Number of items per page
  - `sortBy` (default: id) - Field to sort by
  - `sortDir` (default: ASC) - Sort direction (ASC/DESC)
  - `search` (optional) - Search keyword for name or course
- **Example:** `GET /api/students?page=0&size=10&sortBy=name&sortDir=ASC&search=Computer`
- **Response:** 200 OK (with pagination metadata)

### 3. Get Student by ID
- **GET** `/api/students/{id}`
- **Example:** `GET /api/students/1`
- **Response:** 200 OK
  ```json
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  }
  ```

### 4. Update Student
- **PUT** `/api/students/{id}`
- **Request Body:** (same as Create Student)
- **Example:** `PUT /api/students/1`
- **Response:** 200 OK (updated student object)

### 5. Delete Student
- **DELETE** `/api/students/{id}`
- **Example:** `DELETE /api/students/1`
- **Response:** 204 No Content

## Validation Rules

- **Name:** Required, 2-100 characters
- **Email:** Required, valid email format, unique
- **Course:** Required, 2-100 characters
- **Age:** Required, minimum 18, maximum 100

## HTTP Status Codes

- **200 OK** - Successful GET/PUT request
- **201 Created** - Successful POST request
- **204 No Content** - Successful DELETE request
- **400 Bad Request** - Validation errors
- **404 Not Found** - Resource not found
- **409 Conflict** - Duplicate resource (e.g., email already exists)
- **500 Internal Server Error** - Unexpected server error

## Swagger UI Documentation

Once the application is running, access the Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

Or alternatively:
```
http://localhost:8080/swagger-ui/index.html
```

## Testing the API

### Using cURL

**Create a student:**
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "course": "Computer Science",
    "age": 20
  }'
```
![alt text](image-1.png)
**Get all students:**
```bash
curl http://localhost:8080/api/students
```
![alt text](image.png)
**Get student by ID:**
```bash
curl http://localhost:8080/api/students/1
```
![alt text](image-2.png)

**Update student:**
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "email": "john.smith@example.com",
    "course": "Mathematics",
    "age": 21
  }'
```
![alt text](image-3.png)

**Delete student:**
```bash
curl -X DELETE http://localhost:8080/api/students/1
```
![alt text](<Postman Test Screenshots/DELETE.png>)

**Search students:**
```bash
curl "http://localhost:8080/api/students?search=Computer&page=0&size=10"
```

### Using Postman

Import the API endpoints into Postman and test all CRUD operations. Make sure to set the `Content-Type` header to `application/json` for POST and PUT requests.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/studentmanagement/
│   │       ├── StudentManagementApplication.java
│   │       ├── config/
│   │       │   └── OpenApiConfig.java
│   │       ├── controller/
│   │       │   └── StudentController.java
│   │       ├── dto/
│   │       │   ├── StudentRequest.java
│   │       │   └── StudentResponse.java
│   │       ├── entity/
│   │       │   └── Student.java
│   │       ├── exception/
│   │       │   ├── DuplicateResourceException.java
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   └── ResourceNotFoundException.java
│   │       ├── repository/
│   │       │   └── StudentRepository.java
│   │       └── service/
│   │           ├── StudentService.java
│   │           └── StudentServiceImpl.java
│   └── resources/
│       └── application.properties
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Troubleshooting

### Database Connection Issues

- Ensure MySQL is running and accessible
- Verify database credentials in `application.properties`
- Check if the database `student_management` exists or can be created
- Ensure MySQL allows connections from your IP address

### Port Already in Use

If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```

### Docker Issues

- Ensure Docker and Docker Compose are installed and running
- Check Docker logs: `docker-compose logs`
- Rebuild containers: `docker-compose up --build`

## License

This project is for educational purposes.

