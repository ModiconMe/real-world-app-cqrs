package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import com.modiconme.realworld.it.base.TestDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TagListTest {

    static Stream<Arguments> failureWithInvalidTags() {
        return Stream.of(
                Arguments.of(List.of("")),
                Arguments.of(List.of(TestDataGenerator.uniqString(129)))
        );
    }

    static Stream<Arguments> success() {
        return Stream.of(
                Arguments.of(List.of("tag")),
                Arguments.of(List.of(TestDataGenerator.uniqString(128)))
        );
    }

    @ParameterizedTest
    @MethodSource
    void success(List<String> tags) {
        Result<TagList> result = TagList.emerge(tags);

        assertTrue(result.isSuccess());
        assertEquals(tags, result.getData().getValue());
    }

    @Test
    void successWithDuplicatedValues() {
        Result<TagList> result = TagList.emerge(List.of("1", "1", "2"));

        assertTrue(result.isSuccess());
        assertEquals(List.of("1", "2"), result.getData().getValue());
    }

    @ParameterizedTest
    @MethodSource
    void failureWithInvalidTags(List<String> tags) {
        Result<TagList> result = TagList.emerge(tags);

        assertFalse(result.isSuccess());
        assertEquals("Invalid tags: '%s'".formatted(tags), result.getError().getMessage());
    }

    @Test
    void failureWithNullList() {
        Result<TagList> result = TagList.emerge(null);

        assertFalse(result.isSuccess());
        assertEquals("Invalid tag list", result.getError().getMessage());
    }

    @Test
    void failureWithOneInvalidTagsInList() {
        Result<TagList> result = TagList.emerge(List.of("  ", "okTag"));

        assertFalse(result.isSuccess());
        assertEquals("Invalid tags: '%s'".formatted(List.of("")), result.getError().getMessage());
    }
}