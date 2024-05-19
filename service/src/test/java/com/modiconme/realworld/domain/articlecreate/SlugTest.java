package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SlugTest {

    static Stream<Arguments> emergeFromTitle() {
        return Stream.of(
                Arguments.of("BigTitle", "bigtitle"),
                Arguments.of("Big!Title", "bigtitle"),
                Arguments.of("Big\"Title", "bigtitle"),
                Arguments.of("Big#Title", "bigtitle"),
                Arguments.of("Big$Title", "bigtitle"),
                Arguments.of("Big%Title", "bigtitle"),
                Arguments.of("Big&Title", "bigtitle"),
                Arguments.of("Big'Title", "bigtitle"),
                Arguments.of("Big(Title", "bigtitle"),
                Arguments.of("Big)Title", "bigtitle"),
                Arguments.of("Big*Title", "bigtitle"),
                Arguments.of("Big+Title", "bigtitle"),
                Arguments.of("Big,Title", "bigtitle"),
                Arguments.of("Big.Title", "bigtitle"),
                Arguments.of("Big\\Title", "bigtitle"),
                Arguments.of("Big/Title", "bigtitle"),
                Arguments.of("Big:Title", "bigtitle"),
                Arguments.of("Big;Title", "bigtitle"),
                Arguments.of("Big<Title", "bigtitle"),
                Arguments.of("Big=Title", "bigtitle"),
                Arguments.of("Big>Title", "bigtitle"),
                Arguments.of("Big?Title", "bigtitle"),
                Arguments.of("Big@Title", "bigtitle"),
                Arguments.of("Big[Title", "bigtitle"),
                Arguments.of("Big]Title", "bigtitle"),
                Arguments.of("Big^Title", "bigtitle"),
                Arguments.of("Big_Title", "bigtitle"),
                Arguments.of("Big.Title", "bigtitle"),
                Arguments.of("Big`Title", "bigtitle"),
                Arguments.of("Big{Title", "bigtitle"),
                Arguments.of("Big|Title", "bigtitle"),
                Arguments.of("Big}Title", "bigtitle"),
                Arguments.of("Big~Title", "bigtitle"),
                Arguments.of("Big№Title", "bigtitle"),
                Arguments.of("Big_Title", "bigtitle"),
                Arguments.of("Big-Title", "big-title"),
                Arguments.of("Big.123Title", "big123title"),
                Arguments.of(" Big  Title ", "big-title")
        );
    }

    @Test
    void failure() {
        Result<Slug> result = Title.emerge("!").flatMap(Slug::emerge);

        assertFalse(result.isSuccess());
        assertEquals("Invalid title for slug: '%s'".formatted("!"), result.getError().getMessage());
    }

    @Test
    void failureWithNullTitle() {
        Result<Slug> result = Slug.emerge(null);

        assertFalse(result.isSuccess());
        assertEquals("Invalid title for slug", result.getError().getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void emergeFromTitle(String title, String expectedSlug) {
        Result<Slug> result = Title.emerge(title).flatMap(Slug::emerge);

        assertTrue(result.isSuccess());
        assertEquals(expectedSlug, result.getData().getValue());
    }

}