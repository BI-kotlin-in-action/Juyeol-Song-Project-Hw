#!/bin/bash

# Remove all stopped containers
docker rm $(docker ps -a -q) -f

# Remove all images
docker rmi $(docker images -q) -f

# Remove volumes
docker volume prune -f

# Remove networks
docker network prune -f

# Build Docker images using Docker Compose
docker-compose build --no-cache

# Run Docker containers
docker-compose up -d
