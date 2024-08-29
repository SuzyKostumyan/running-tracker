# Running Tracker

**Running Tracker** is a RESTful API application that allows users to track their running activities, including start and finish locations, times, and distances. The application also provides statistical insights for each user, such as the total number of runs, total distance covered, and average speed.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Database](#database)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Testing](#testing)

## Features

- **User Management**: Create, update, delete, and retrieve user information.
- **Run Tracking**: Start and finish runs with details such as start and finish locations, times, and distances.
- **Statistics**: Calculate and retrieve user statistics, including the total number of runs, total distance covered, and average speed.
- **Run History**: Retrieve a list of all runs for a specific user with optional filtering by date range.

## Technology Stack

- **Java 21**
- **Spring Boot 3.2.2**
- **MongoDB**: Used as the primary database for storing user and run information.
- **ModelMapper**: For object mapping between DTOs and entities.
- **Swagger**: For API documentation and testing, accessible at [http://localhost:4444/swagger-ui/index.html](http://localhost:4444/swagger-ui/index.html) when the application is running.
- **Maven**: For dependency management and build automation.

## Database

- **Database**: MongoDB
- **Collections**:
  - **user**: Stores user details including first name, last name, birth date, and sex.
  - **run**: Stores details of each run including user ID, start and finish locations, start and finish times, distance, and average speed.
  
MongoDB is used for its scalability and flexibility in handling the JSON-like documents that represent users and runs.


## API Endpoints

### User Endpoints

- **POST /api/v1/users**
  - Create a new user.
  - **Request Body**: `UserRequest`
  - **Response**: `UserResponse`

- **PUT /api/v1/users/{id}**
  - Update an existing user.
  - **Path Parameter**: `id` (User ID)
  - **Request Body**: `UserRequest`
  - **Response**: `UserResponse`

- **GET /api/v1/users/{id}**
  - Retrieve a user by ID.
  - **Path Parameter**: `id` (User ID)
  - **Response**: `UserResponse`
 
- **GET /api/v1/users**
  - Retrieve a paginated list of all users.
  - **Query Parameters**: 
    - `page` (optional): The page number to retrieve (default is 0).
    - `size` (optional): The number of users per page (default is 20).
    - `sort` (optional): Sorting criteria in the format `property(,asc|desc)`.
  - **Response**: `Page<UserResponse>`
    - A paginated list of users where each user is represented as a `UserResponse`.

- **DELETE /api/v1/users/{id}**
  - Delete a user by ID.
  - **Path Parameter**: `id` (User ID)
  - **Response**: `UserResponse` of the deleted user

### Run Endpoints

- **POST /api/v1/runs/start**
  - Start a new run for a user.
  - **Request Body**: `StartRunRequest`
  - **Response**: `RunResponse`

- **POST /api/v1/runs/finish**
  - Finish an ongoing run for a user.
  - **Request Body**: `FinishRunRequest`
  - **Response**: `RunResponse`

- **GET /api/v1/runs/user/{userId}**
  - Retrieve all runs for a user, optionally filtered by date range.
  - **Path Parameter**: `userId`
  - **Query Parameters**: `fromDateTime`, `toDateTime` (optional)
  - **Response**: `List<RunResponse>`

- **GET /api/v1/runs/user/{userId}/statistics**
  - Retrieve statistics for a user, including the number of runs, total distance, and average speed.
  - **Path Parameter**: `userId`
  - **Query Parameters**: `fromDateTime`, `toDateTime` (optional)
  - **Response**: `UserStatisticsResponse`

## Getting Started

### Prerequisites

- **Java 21**
- **MongoDB**: Install and run a local or remote MongoDB instance.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SuzyKostumyan/running-tracker.git
   cd running-tracker
   
**Configure the database connection**:

- Open src/main/resources/application.properties.
- Set the spring.data.mongodb.uri property to your MongoDB connection string.

2. **Build the project**:

   ```bash
   mvn clean install

3. **Run the application**:

   ```bash
   mvn spring-boot:run

## Testing

The **Running Tracker** project includes both unit and integration tests to ensure the correctness and reliability of the application.

### Unit Tests

- **Purpose**: Unit tests are designed to test individual components of the application, such as services and utility classes, in isolation.
- **Location**: The unit tests are located in the `src/test/java/running.tracker/unit` directory.

### Integration Tests

- **Purpose**: Integration tests are designed to test the application’s components as a whole, including their interactions with external systems like databases.
- **Location**: The integration tests are also located in the `src/test/java/running.tracker/integration` directory.
