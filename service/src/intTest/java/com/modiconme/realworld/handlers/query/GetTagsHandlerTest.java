package com.modiconme.realworld.handlers.query;

import com.modiconme.realworld.application.query.GetTagsHandler;
import com.modiconme.realworld.core.BaseTest;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.query.GetTags;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.modiconme.realworld.core.data.var2.GenericBuilder.uniqString;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class GetTagsHandlerTest extends BaseTest {

    private final GetTagsHandler handler;

    @Test
    void shouldGetTags() {
        // given
        db.persisted(new TagEntity(uniqString()), new TagEntity(uniqString()), new TagEntity(uniqString()));

        // when
        List<String> tags = handler.handle(new GetTags()).getTags();

        // then
        assertThat(tags).isNotEmpty();
    }

}
