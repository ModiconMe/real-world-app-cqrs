package com.modiconme.realworld.application.command;

import com.modiconme.realworld.application.ArticleMapper;
import com.modiconme.realworld.application.service.SlugService;
import com.modiconme.realworld.command.CreateArticle;
import com.modiconme.realworld.command.CreateArticleResult;
import com.modiconme.realworld.cqrs.CommandHandler;
import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.TagEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import com.modiconme.realworld.domain.repository.TagRepository;
import com.modiconme.realworld.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.modiconme.realworld.infrastructure.utils.exception.ApiException.exception;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateArticleHandler implements CommandHandler<CreateArticleResult, CreateArticle> {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final SlugService slugService;

    @Override
    @Transactional
    public CreateArticleResult handle(CreateArticle cmd) {
        String slug = slugService.createSlug(cmd.getTitle());

        // check slug duplicate
        if (articleRepository.findBySlug(slug).isPresent())
            throw exception(HttpStatus.UNPROCESSABLE_ENTITY, "article with slug [%s] is already exist", slug);

        // find author
        String authorUsername = cmd.getAuthorUsername();
        UserEntity user = userRepository.findByUsername(authorUsername)
                .orElseThrow(() -> exception(HttpStatus.NOT_FOUND, "user with username [%s] is not found", authorUsername));

        // check tags
        List<TagEntity> tags = new ArrayList<>();
        if (cmd.getTagList() != null) {
            tags = cmd.getTagList().stream()
                    .map(t -> tagRepository.findByTagName(t)
                            .orElseGet(() -> tagRepository.save(new TagEntity(t))))
                    .toList();
        }

        ArticleEntity article = ArticleEntity.builder()
                .slug(slug)
                .title(cmd.getTitle())
                .body(cmd.getBody())
                .description(cmd.getDescription())
                .author(user)
                .tags(tags)
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();
        articleRepository.save(article);
        log.info("create article {}", article);

        return new CreateArticleResult(ArticleMapper.mapToDto(article, user));
    }

}
