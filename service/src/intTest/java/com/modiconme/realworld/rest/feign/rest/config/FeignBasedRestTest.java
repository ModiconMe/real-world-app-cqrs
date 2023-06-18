package com.modiconme.realworld.rest.feign.rest.config;

import com.modiconme.realworld.rest.feign.client.UserClient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.util.TestSocketUtils;
import org.testcontainers.containers.PostgreSQLContainer;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EnableFeignClients(basePackageClasses = UserClient.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(ServerPortCustomizer.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class FeignBasedRestTest {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

    @BeforeAll
    static void runContainer() {
        if (System.getProperty("server.port") == null) {
            int port = TestSocketUtils.findAvailableTcpPort();
            System.setProperty("server.port", String.valueOf(port));
        }

        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }

}
