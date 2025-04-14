#!/bin/bash

# Navigate to the directory containing your docker-compose.yml file

project_root="/Users/nitinyadav/Downloads/workspace/Other Projects for Learning/Zomato/"

# Start the Docker Compose services
echo "Starting Docker Compose services..."
docker-compose up --build -d

# Check if docker compose up was successful.
if [ $? -ne 0 ]; then
  echo "Error: Docker Compose up failed."
  exit 1
fi

# Wait for the Spring Boot application to start (optional, but recommended)
echo "Waiting for Spring Boot application to become healthy..."

# check if spring boot app is healthy.
docker ps --filter "name=spring-boot-app" --format "{{.Status}}" | grep -q "healthy"

if [ $? -ne 0 ]; then
  echo "Error: Spring Boot application did not become healthy in time or at all. Please check the logs."
  exit 1
fi

echo "All services, including Spring Boot, are up and running."

# You can add more commands here, like running tests, etc.

# Optional: To stop the containers later, you can use:
# docker-compose down