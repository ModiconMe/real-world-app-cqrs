FROM bellsoft/liberica-openjdk-debian:17

ARG WORKDIR="/application"
ARG JAR_NAME="application.jar"
ARG LOGS_DIR="logs"

# создаем пользователя и папку для логов, также меняем владельца этой папки,
# чтобы можно было писать в нее
WORKDIR ${WORKDIR}
RUN addgroup spring-boot-group && adduser --ingroup spring-boot-group spring-boot && \
    mkdir -p ${LOGS_DIR} && chown -R spring-boot ${LOGS_DIR}

VOLUME ${WORKDIR}/${LOGS_DIR}

USER spring-boot:spring-boot-group

COPY service/build/libs/service.jar ./${JAR_NAME}
EXPOSE 8080
ENV JAVA_OPTS="-Xms128m -Xmx256m"

#1 ENTRYPOINT java ${JAVA_OPTS} -jar application.jar

#2 ENTRYPOINT ["sh", "-c"]
#2 CMD ["java ${JAVA_OPTS} -jar application.jar"]

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar ${JAR_NAME}"]

