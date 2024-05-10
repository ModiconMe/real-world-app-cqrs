package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BioTest {

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of(TestDataGenerator.uniqString(256))
        );
    }

    static Stream<Arguments> failure() {
        return Stream.of(
                Arguments.of(TestDataGenerator.uniqString(257))
        );
    }

    @ParameterizedTest
    @MethodSource
    @NullSource
    @EmptySource
    void success(String bio) {
        Result<Bio> result = Bio.emerge(bio);
        assertTrue(result.isSuccess());
        assertEquals(Optional.ofNullable(bio), result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failure(String bio) {
        Result<Bio> result = Bio.emerge(bio);
        assertTrue(result.isFailure());
        assertEquals("Invalid bio value", result.getError().getMessage());
    }
}