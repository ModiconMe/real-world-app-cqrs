#FROM openjdk:17-jdk-alpine
FROM bellsoft/liberica-openjdk-debian:17

RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot
USER spring-boot:spring-boot-group

WORKDIR /application

COPY service/build/libs/service.jar ./application.jar
EXPOSE 8080
ENV JAVA_OPTS="-Xms128m -Xmx256m"

#1 ENTRYPOINT java ${JAVA_OPTS} -jar application.jar

#2 ENTRYPOINT ["sh", "-c"]
#2 CMD ["java ${JAVA_OPTS} -jar application.jar"]

#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar application.jar"]

