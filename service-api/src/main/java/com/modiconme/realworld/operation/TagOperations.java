package com.modiconme.realworld.operation;

import com.modiconme.realworld.query.GetTagsResult;
import org.springframework.web.bind.annotation.GetMapping;

public interface TagOperations {

    @GetMapping("/tags")
    GetTagsResult getTags();

}
