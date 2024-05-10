package com.modiconme.realworld.it.base.extension;

import com.modiconme.realworld.domain.userlogin.LoginUserDto;
import com.modiconme.realworld.domain.userregister.RegisteredUserDto;
import com.modiconme.realworld.it.base.api.AuthClient;
import org.junit.jupiter.api.extension.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class AuthExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(AuthExtension.class);

    public static void setUser(ExtensionContext context, TestUser user) {
        context.getStore(NAMESPACE).put("user", user);
    }

    public static TestUser getUser(ExtensionContext context) {
        return context.getStore(NAMESPACE).get("user", TestUser.class);
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        AuthClient authClient = SpringExtension
                .getApplicationContext(extensionContext)
                .getBean(AuthClient.class);

        Auth auth = findAnnotation(extensionContext.getRequiredTestMethod(), Auth.class)
                .orElse(findAnnotation(extensionContext.getRequiredTestClass(), Auth.class).orElse(null));


        if (auth != null) {
            TestUser user;
            if (auth.register()) {
                RegisteredUserDto registeredUser = authClient.register();
                user = new TestUser(
                        registeredUser.email(),
                        registeredUser.token(),
                        registeredUser.username(),
                        registeredUser.bio(),
                        registeredUser.image()
                );
            } else {
                LoginUserDto loginUser = authClient.authenticate(auth.username(), auth.password());
                user = new TestUser(
                        loginUser.email(),
                        loginUser.token(),
                        loginUser.username(),
                        loginUser.bio(),
                        loginUser.image()
                );
            }
            setUser(extensionContext, user);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(TestUser.class);
    }

    @Override
    public TestUser resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getUser(extensionContext);
    }
}
