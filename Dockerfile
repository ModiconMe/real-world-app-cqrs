FROM openjdk:17-jdk-alpine
COPY build/libs/application-1.0.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application-1.0.jar"]