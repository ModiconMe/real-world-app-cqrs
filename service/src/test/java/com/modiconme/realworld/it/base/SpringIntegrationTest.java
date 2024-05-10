package com.modiconme.realworld.it.base;

import com.modiconme.realworld.infrastructure.security.jwt.JwtConfig;
import com.modiconme.realworld.it.base.api.AuthClient;
import com.modiconme.realworld.it.base.api.AuthorizationInterceptor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@IntSpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public abstract class SpringIntegrationTest {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1")
            .withReuse(true);

    @Autowired
    protected TestDbFacade db;
    @Autowired
    protected AuthClient authenticator;
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected JwtConfig jwtConfig;
    @Autowired
    protected AuthClient authClient;

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @Autowired
    public void setTestRestTemplate(TestRestTemplate testRestTemplate, AuthorizationInterceptor authorizationInterceptor) {
        this.testRestTemplate = testRestTemplate;
//        testRestTemplate.getRestTemplate().setInterceptors(List.of(authorizationInterceptor));
    }

    protected @NotNull HttpHeaders getAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, getTokenWithPrefix(token));
        return headers;
    }

    protected @NotNull String getTokenWithPrefix(String token) {
        return jwtConfig.getTokenPrefix() + " " + token;
    }
}
