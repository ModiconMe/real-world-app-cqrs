package com.modiconme.realworld.application.query;

import com.modiconme.realworld.cqrs.QueryHandler;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.repository.TagRepository;
import com.modiconme.realworld.query.GetTags;
import com.modiconme.realworld.query.GetTagsResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class GetTagsHandler implements QueryHandler<GetTagsResult, GetTags> {

    private final TagRepository tagRepository;

    @Override
    @Transactional(readOnly = true)
    public GetTagsResult handle(GetTags query) {
        return new GetTagsResult(
                StreamSupport
                .stream(tagRepository.findAll().spliterator(), false)
                .map(TagEntity::getTagName)
                .toList()
        );
    }

}
