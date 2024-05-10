package com.modiconme.realworld.it.base.assertions;

import com.modiconme.realworld.infrastructure.web.controller.RestResponse;
import org.assertj.core.api.AbstractAssert;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SuppressWarnings("UnusedReturnValue")
public final class RestResponseAssertion<T> extends AbstractAssert<RestResponseAssertion<T>, ResponseEntity<RestResponse<T>>> {

    public RestResponseAssertion(ResponseEntity<RestResponse<T>> restResponseResponseEntity) {
        super(restResponseResponseEntity, RestResponseAssertion.class);
    }

    public static <T> RestResponseAssertion<T> assertThat(ResponseEntity<RestResponse<T>> actual) {
        return new RestResponseAssertion<>(actual);
    }

    public RestResponseAssertion<T> hasStatusCodeEquals(HttpStatusCode expectedStatusCode) {
        isNotNull();
        HttpStatusCode actualStatusCode = actual.getStatusCode();
        if (actualStatusCode != expectedStatusCode) {
            failWithMessage("Status code not equals actual=%s, expected", actualStatusCode, expectedStatusCode);
        }
        return this;
    }

    public RestResponseAssertion<T> hasErrorEquals(String expectedError) {
        isNotNull();
        String actualError = Objects.requireNonNull(actual.getBody()).getError();
        if (!Objects.equals(actualError, expectedError)) {
            failWithMessage("Error must be equal: actual=%s, expected=%s", actualError, expectedError);
        }
        return this;
    }

    public RestResponseAssertion<T> hasDataEquals(T expectedData) {
        isNotNull();
        T actualData = Objects.requireNonNull(actual.getBody()).getData();
        if (!Objects.equals(actualData, expectedData)) {
            failWithMessage("Data must be equals: actual=%s, expected=%s", actualData, expectedData);
        }
        return this;
    }

    @Override
    public RestResponseAssertion<T> as(String description, Object... args) {
        return super.as(description, args);
    }
}
