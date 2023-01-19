package com.modiconme.realworld.rest.config;

import com.modiconme.realworld.client.ArticleClient;
import com.modiconme.realworld.client.UserClient;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.TestSocketUtils;

@EnableFeignClients(basePackageClasses = UserClient.class)
@Import(ServerPortCustomizer.class)
@ExtendWith(FeignBasedRestTest.Before.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FeignBasedRestTest {

    public static class Before implements BeforeAllCallback {

        @Override
        public void beforeAll(ExtensionContext context) {
            if (System.getProperty("server.port") == null) {
                int port = TestSocketUtils.findAvailableTcpPort();
                System.setProperty("server.port", String.valueOf(port));
            }
        }

    }

}
