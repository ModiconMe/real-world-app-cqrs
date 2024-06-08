FROM bellsoft/liberica-openjdk-debian:17

ARG UID=1000
ARG GID=1000

ARG WORKDIR="/application"
ARG JAR_NAME="application.jar"
ARG LOGS_DIR="logs"

# создаем пользователя и папку для логов, также меняем владельца этой папки,
# чтобы можно было писать в нее
WORKDIR ${WORKDIR}
RUN addgroup --gid ${GID} spring-boot-group && \
    adduser --gid ${GID} --uid ${UID} spring-boot && \
    mkdir -p ${LOGS_DIR} && \
    chown -R spring-boot:spring-boot-group ${WORKDIR}

VOLUME ${WORKDIR}/${LOGS_DIR}

USER spring-boot:spring-boot-group

COPY service/build/libs/service.jar ./${JAR_NAME}
EXPOSE 8080
ENV JAVA_OPTS="-Xms128m -Xmx256m"

#1 ENTRYPOINT java ${JAVA_OPTS} -jar application.jar

#2 ENTRYPOINT ["sh", "-c"]
#2 CMD ["java ${JAVA_OPTS} -jar application.jar"]

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar application.jar"]

