package com.modiconme.realworld.infrastructure.web.controller;

import com.modiconme.realworld.application.service.AuthenticationContextHolderService;
import com.modiconme.realworld.cqrs.Bus;
import com.modiconme.realworld.operation.TagOperations;
import com.modiconme.realworld.query.GetTags;
import com.modiconme.realworld.query.GetTagsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/")
public class TagController implements TagOperations {

    private final Bus bus;

    @Override
    public GetTagsResult getTags() {
        return bus.executeQuery(new GetTags());
    }
}
