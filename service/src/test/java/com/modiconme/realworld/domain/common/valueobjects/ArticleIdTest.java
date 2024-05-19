package com.modiconme.realworld.domain.common.valueobjects;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticleIdTest {

    @Test
    void success() {
        Result<ArticleId> result = ArticleId.emerge(1L);
        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData().getValue());
    }

    @Test
    void failure() {
        Result<ArticleId> result = ArticleId.emerge(-1L);
        assertTrue(result.isFailure());
        assertEquals("Article not found", result.getError().getMessage());
    }

}