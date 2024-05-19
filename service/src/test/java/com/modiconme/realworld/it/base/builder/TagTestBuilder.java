package com.modiconme.realworld.it.base.builder;

import com.modiconme.realworld.it.base.TestDataGenerator;
import com.modiconme.realworld.it.base.repository.TagEntity;

public class TagTestBuilder implements TestBuilder<TagEntity> {

    private Long id;
    private String tagName = TestDataGenerator.uniqString(5);

    private TagTestBuilder() {
    }

    public static TagTestBuilder aTag() {
        return new TagTestBuilder();
    }

    @Override
    public TagEntity build() {
        return new TagEntity(id, tagName);
    }

    public Long getId() {
        return id;
    }

    public TagTestBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public TagTestBuilder tagName(String tagName) {
        this.tagName = tagName;
        return this;
    }
}
