# Employee Management System

A robust Spring Boot application for managing employees and users with JWT authentication and role-based access control.

## Features

### Authentication & Security
- JWT-based authentication
- Role-based access control (Admin and User roles)
- Password encryption using BCrypt
- Secure endpoints with Spring Security
- Token expiration (10 hours)

### Employee Management
- CRUD operations for employees
- Pagination support for employee listing
- Data validation for employee records
- Unique email constraint for employees
- Fields include: first name, last name, email, phone, department, and salary

### User Management (Admin Only)
- Create new users
- Update existing users
- Delete users
- Username uniqueness validation
- Role assignment capabilities

## Technical Stack
- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Tokens)
- Spring JDBC Template
- PostgreSQL Database
- Lombok
- BCrypt Password Encoder

## API Endpoints

### Authentication
- `POST /api/v1/auth/login` - Login and get JWT token

### Employee Management
- `POST /api/v1/employees/create` - Create new employee (Authenticated)
- `GET /api/v1/employees` - Get all employees with pagination (Authenticated)
- `GET /api/v1/employees/{id}` - Get employee by ID (Authenticated)
- `PUT /api/v1/employees/update/{id}` - Update employee (Admin only)
- `DELETE /api/v1/employees/delete/{id}` - Delete employee (Admin only)

### User Management
- `POST /api/v1/user/create` - Create new user (Admin only)
- `POST /api/v1/user/update/{username}` - Update user (Admin only)
- `DELETE /api/v1/user/delete/{username}` - Delete user (Admin only)

## Database Schema
- `src/main/resources/db/schema.sql` - Contains the SQL script to create the database schema
- `src/main/resources/db/data.sql` - Contains the SQL script to insert sample data into the database

## Security Rules

### Authentication Required
- All endpoints except login require valid JWT token
- Token must be included in Authorization header

### Role-Based Access
- Admin role required for:
  - All user management operations
  - Employee updates and deletions
- Authenticated users can:
  - View employee listings
  - View individual employee details
  - Create new employees

## Validation Rules

### Employee Validation
- First name and last name required
- Valid email format required
- Phone number must be 10 digits
- Department name required
- Salary cannot be negative

### User Validation
- Username must be unique
- Password is encrypted before storage
- Valid role assignment required

## Error Handling
- Custom exception handling for validation errors
- Proper HTTP status codes for different scenarios
- Meaningful error messages for client feedback

## Diagrams

### ER Diagram
Path: `docs/er-diagrams/ER Diagram.pdf`

### Sequence Diagram
Path: `docs/sequence-diagrams/your-sequence-diagram.pdf`

## How to Run

### Configure Database
- Create PostgreSQL database
- Update `application.properties` with database credentials

### Build Project
- Clone the repository
  ```sh
  git clone <https://github.com/shobikan/Employee_Management_System.git>