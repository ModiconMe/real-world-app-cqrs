package com.modiconme.realworld.it.base;

import com.modiconme.realworld.it.base.api.AuthClient;
import com.modiconme.realworld.it.base.api.AuthorizationInterceptor;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@IntSpringBootTest
@AutoConfigureTestEntityManager
public abstract class SpringIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

    @Autowired
    protected TestDbFacade db;
    @Autowired
    protected AuthClient authenticator;
    @Autowired
    protected TestRestTemplate testRestTemplate;

    @BeforeAll
    static void beforeAll(@Autowired TestRestTemplate testRestTemplate, @Autowired AuthorizationInterceptor authorizationInterceptor) {
        testRestTemplate.getRestTemplate().setInterceptors(List.of(authorizationInterceptor));
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
