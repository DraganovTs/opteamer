# Opteamer

Opteamer is a comprehensive application built to streamline the management of operations and inventory in a medical environment. It offers APIs for managing assets, team members, operations, assessments, and user registrations.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Documentation](#api-documentation)
- [Installation](#installation)
- [Usage](#usage)
- [Docker](#docker)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Asset Management**: Handle the creation, reading, updating, and deletion of medical assets such as equipment, tools, and machines.
- **Team Member Management**: Manage medical team members, their roles, and responsibilities.
- **Operation Scheduling**: Plan and manage surgeries and other medical operations.
- **Pre-Operative Assessments**: Conduct and manage assessments before operations.
- **Inventory Management**: Keep track of room inventories, including the count of assets available for operations.
- **User Registration**: Enable new users to register and manage their access within the system.

## Technologies Used

- **Java**: Core language for application development.
- **Spring Boot**: Framework for building the RESTful API and managing business logic.
- **Spring Data JPA**: Manages database interactions for persistent data.
- **Hibernate**: ORM framework for managing data persistence.
- **OpenAPI (Swagger)**: API documentation and testing tool.
- **H2 Database**: In-memory database for local development and testing.
- **PostgreSQL**: Database for production and advanced development use.

## API Documentation

The application offers a RESTful API documented using OpenAPI (Swagger). You can access the documentation and explore the available endpoints via the following URLs:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
  Provides a user-friendly interface to test the APIs.
  
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)  
  This endpoint returns the full OpenAPI specification in JSON format.

## Installation

### Prerequisites

- **Java 17** or higher
- **Maven**
- **PostgreSQL** (or H2 for local development)

### Steps to Install

1. Clone the repository to your local machine:

   ```bash
   git clone https://github.com/yourusername/opteamer.git

2. Navigate to the project directory:

   ```bash
   cd opteamer

3. Build the application:

   ```bash
   ./mvnw clean install

4. Run the application:

   ```bash
   ./mvnw spring-boot:run

## Usage
Once the application is running, you can access the Swagger UI to interact with the APIs, test requests, and explore the available operations. To access Swagger UI, visit http://localhost:8080/swagger-ui.html in your browser.

## Docker
To run the application using Docker and initialize the database with data, follow these steps:

Prerequisites
Docker: Ensure Docker is installed and running on your machine.

### Steps to Run with Docker

1. Build the Docker images:
  Navigate to the project directory and run
   ```bash
   docker-compose build

2. Start the containers:

   ```bash
   docker-compose up

3.Access the application:

 Once the containers are up, you can access the Swagger UI at http://localhost:8080/swagger-ui.html.

## Contributing
Contributions are welcome! If you find a bug or have suggestions for improvements, feel free to open an issue or submit a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.


