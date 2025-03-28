# Employee Management System

A web application for managing employees built with GWT, Java 8, and PostgreSQL.

## Prerequisites

- Java 8 JDK
- Maven
- Docker and Docker Compose

## Building and Running the Application

1. Clone this repository:

   ```
   git clone https://github.com/yourusername/employee-management.git
   cd employee-management
   ```

2. Run the build script:

   ```
   ./build.sh
   ```

   This will:

   - Compile the GWT application
   - Build a Docker image for the web application
   - Start the PostgreSQL database and web application containers

3. Access the application at http://localhost:8080

## Manual Build Steps

If you prefer to build and run the application manually:

1. Compile the application:

   ```
   mvn clean package
   ```

2. Build and start the Docker containers:
   ```
   docker-compose up --build -d
   ```

## Stopping the Application

To stop the application and remove the containers:
