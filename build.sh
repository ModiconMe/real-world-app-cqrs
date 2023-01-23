#/bin/bash
./gradlew clean build
docker build -t modiconme/real-world-backend:latest .
docker build -t modiconme/real-world-frontend:latest frontend/
docker login -u $1 -p $2
docker push modiconme/real-world-backend:latest
docker push modiconme/real-world-frontend:latest

