#!/bin/bash

# Clean GWT cache directories
echo "Cleaning GWT cache..."
rm -rf war/WEB-INF/deploy/
rm -rf war/your_module_name/
rm -rf .gwt-tmp/
rm -rf gwt-unitCache/

# Clear browser cache notification
echo "IMPORTANTE: Recuerda limpiar la caché de tu navegador (Ctrl+F5 o Cmd+Shift+R)"

# Build the Maven project with clean
echo "Building the Maven project..."
mvn clean package -U

# Stop existing containers to ensure clean state
echo "Stopping existing containers..."
docker-compose down

# Remove Docker volumes to ensure clean state
echo "Removing Docker volumes..."
docker-compose down -v

# Build and start Docker containers
echo "Building and starting Docker containers..."
docker-compose up --build -d

echo "Application is starting at http://localhost:8080"
echo "It may take a few moments for the application to fully initialize."