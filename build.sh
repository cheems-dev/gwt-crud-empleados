#!/bin/bash

# Build the Maven project
echo "Building the Maven project..."
mvn clean package

# Build and start Docker containers
echo "Building and starting Docker containers..."
docker-compose up --build -d

echo "Application is starting at http://localhost:8080"
echo "It may take a few moments for the application to fully initialize." 