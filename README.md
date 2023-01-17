> ### Spring Boot application

Spring boot core app

### Highlights
- 

Также:
* 

### Technology
- Spring Boot 3.0.0 и Java 17
- Spring Data JPA + Hibernate + H2-database
- Spring Security 6 + jwt
- Logback
- Spring Validation для валидации rest запросов
- Jackson для сериализации и дессериализации запросов и ответов
- JUnit 5 + AssertJ + Mockito для тестирования
- Docker для [запуска](#Docker-run) в контейнере из Dockerfile

### Getting started
Требуется Java 17 или выше

    ./gradlew bootRun

Для тестирования работоспособности приложения в браузере http://localhost:8080/api/tags.  
Или же через команду в терминале

    curl http://localhost:8080/api

### Testing

Для запуска тестов следующая команда

    ./gradlew test

Процент покрытия строк кода тестами --%.

### Docker-run

Для запуска приложения в докер контейнере в корне проекта запускаем команду
для построения докер образа.

`docker build -t realworld:v1 .`

После построения образа создаем и запускаем контейер с помощью docker run

`docker run --name realworld -p 8080:8080 realworld:v1`

