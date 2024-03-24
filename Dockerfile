#FROM openjdk:17-jdk-alpine
FROM bellsoft/liberica-openjdk-debian:17

RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot
USER spring-boot:spring-boot-group

WORKDIR /application

COPY service/build/libs/service.jar ./application.jar
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -jar application.jar