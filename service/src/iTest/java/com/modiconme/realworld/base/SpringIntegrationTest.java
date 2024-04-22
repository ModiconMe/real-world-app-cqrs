package com.modiconme.realworld.base;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@IntSpringBootTest
@AutoConfigureTestEntityManager
//@Sql(scripts = "classpath:sql/data.sql")
public abstract class SpringIntegrationTest {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

    @Autowired
    protected TestDbFacade db;

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
