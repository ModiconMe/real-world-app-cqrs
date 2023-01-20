FROM openjdk:17-jdk-alpine
COPY service/build/libs/application-1.0.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "service-1.0.jar"]