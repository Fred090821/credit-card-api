# Credit Card API

## Overview

The Credit Card API is a RESTful service designed to manage credit card information. It allows users to add new credit cards, retrieve all stored credit cards, and ensures data integrity through validation checks.

## Features

- Add new credit cards with validation for card number and card limit
- Retrieve a list of all stored credit cards
- Validation of credit card numbers using the Luhn 10 algorithm
- Prevention of duplicate card numbers
- RESTful API design with proper HTTP status codes
- Comprehensive error handling and validation messages

## Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (for development and testing)
- Maven (build tool)
- JUnit 5 and Mockito (for testing)
- Jackson (JSON serialization/deserialization)
- Spring Boot Validation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/credit-card-api.git
   cd credit-card-api
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

   Alternatively, you can run the JAR file:

   ```bash
   java -jar target/credit-card-api-1.0.0.jar
   ```

4. The application will start on `http://localhost:8080`

### Database Access

The application uses H2 in-memory database for development. You can access the H2 console at:

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave blank)

## API Endpoints

### Add Credit Card

**POST** `/api/credit-cards`

Creates a new credit card entry.

#### Request Body

```json
{
  "name": "John Doe",
  "cardNumber": "4111111111111111",
  "limit": 1000.00
}
```

#### Response

- **201 Created** - Card successfully added
- **400 Bad Request** - Invalid input or validation errors
- **409 Conflict** - Duplicate card number already exists
- **500 Internal Server Error** - Unexpected server error

### Get All Credit Cards

**GET** `/api/credit-cards`

Retrieves all stored credit cards.

#### Response

### Card Number Validation

- Must be a valid credit card number (Luhn 10 algorithm)
- Must be unique (no duplicates allowed)
- Must contain only digits
- Must be between 13-19 digits long

### Card Limit Validation

- Must be a positive number
- Must be greater than 0
- Supports decimal values

### Name Validation

- Cannot be null or empty
- Must contain valid characters

## Error Handling

The API provides comprehensive error responses with appropriate HTTP status codes:

- **200 OK** - Request successful
- **201 Created** - Resource successfully created
- **400 Bad Request** - Invalid request format or validation failures
- **409 Conflict** - Resource conflict (e.g., duplicate card number)
- **500 Internal Server Error** - Unexpected server errors


## Testing

Run the test suite using Maven:

```bash
# Run all tests
mvn test

```
