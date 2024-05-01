package com.modiconme.realworld.it.base.extension;

import com.modiconme.realworld.dto.UserDto;
import com.modiconme.realworld.it.base.api.AuthClient;
import org.junit.jupiter.api.extension.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;


public class AuthExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(AuthExtension.class);

    public static void setUser(ExtensionContext context, UserDto user) {
        context.getStore(NAMESPACE).put("user", user);
    }

    public static UserDto getUser(ExtensionContext context) {
        return context.getStore(NAMESPACE).get("user", UserDto.class);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        AuthClient authClient = SpringExtension
                .getApplicationContext(extensionContext)
                .getBean(AuthClient.class);

        Auth auth = findAnnotation(extensionContext.getRequiredTestMethod(), Auth.class)
                .orElse(findAnnotation(extensionContext.getRequiredTestClass(), Auth.class).orElse(null));

        if (auth != null) {
            UserDto user;
            if (auth.register()) {
                user = authClient.register();
            } else {
                user = authClient.authenticate(auth.username(), auth.password());
            }
            setUser(extensionContext, user);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(UserDto.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getUser(extensionContext);
    }
}
