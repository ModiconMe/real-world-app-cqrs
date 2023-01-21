#/bin/bash
#./gradlew clean build
docker build -t modiconme/real-world-app-cqrs:latest .
docker build -t modiconme/real-world-app-cqrs-frontend:latest frontend/
docker login -u $1 -p $2
docker push modiconme/real-world-app-cqrs:latest
docker push modiconme/real-world-app-cqrs-frontend:latest

