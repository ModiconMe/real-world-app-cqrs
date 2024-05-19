package com.modiconme.realworld.domain.articlecreate;

import com.modiconme.realworld.domain.common.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatedCreateArticleRequestTest {

    @Test
    void success() {
        Result<ValidatedCreateArticleRequest> result = ValidatedCreateArticleRequest.emerge(
                new CreateArticleRequest(
                        "title", "description", "body", List.of("tag1", "tag2")
                ),
                1
        );

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().getUserId().getValue());
        assertEquals("title", result.getData().getTitle().getValue());
        assertEquals("description", result.getData().getDescription().getValue());
        assertEquals("body", result.getData().getBody().getValue());
        assertEquals("tag1", result.getData().getTagList().getValue().get(0));
        assertEquals("tag2", result.getData().getTagList().getValue().get(1));
    }

    @Test
    void failure() {
        Result<ValidatedCreateArticleRequest> result = ValidatedCreateArticleRequest.emerge(
                new CreateArticleRequest(
                        "", "description", "body", List.of("tag1", "tag2")
                ),
                1
        );

        assertTrue(result.isFailure());
        assertEquals("Invalid title: ''", result.getError().getMessage());
    }
}