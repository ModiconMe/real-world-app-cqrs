#/bin/bash
#./gradlew clean build
docker build -t modiconme/real-world-app-cqrs:latest .
docker login -u $1 -p $2
docker push modiconme/real-world-app-cqrs:latest
