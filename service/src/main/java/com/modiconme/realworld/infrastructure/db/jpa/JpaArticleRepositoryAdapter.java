package com.modiconme.realworld.infrastructure.db.jpa;

import com.modiconme.realworld.domain.model.ArticleEntity;
import com.modiconme.realworld.domain.model.FollowRelationEntity;
import com.modiconme.realworld.domain.model.UserEntity;
import com.modiconme.realworld.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JpaArticleRepositoryAdapter implements ArticleRepository {

    private final DataArticleRepository repository;

    @Override
    public Optional<ArticleEntity> findBySlug(String slug) {
        return repository.findBySlugIgnoreCase(slug);
    }

    @Override
    public Optional<ArticleEntity> findBySlugSearch(String slug) {
        return repository.findBySlugIgnoreCaseContaining(slug);
    }

    @Override
    public ArticleEntity save(ArticleEntity article) {
        return repository.save(article);
    }

    @Override
    public List<ArticleEntity> findByFilters(String tag, String author, String favoritedBy, String offset, String limit) {
        return repository.findByFilter(tag, author, favoritedBy, new OffsetBasedPageRequest(Integer.parseInt(limit), Integer.parseInt(offset), Sort.by("updatedAt")));
    }

//    @Override
//    public List<ArticleEntity> findByFeed(List<UUID> followees, String offset, String limit) {
//        return repository.findByFeed(followees, new OffsetBasedPageRequest(Integer.parseInt(limit), Integer.parseInt(offset), Sort.by("updatedAt")));
//    }

    @Override
    public List<ArticleEntity> findByFeed(String username, String offset, String limit) {
        return repository.findByFeed(username, new OffsetBasedPageRequest(Integer.parseInt(limit), Integer.parseInt(offset), Sort.by(Sort.Order.desc("updatedAt"))));
    }

    @Override
    public void delete(ArticleEntity article) {
        repository.delete(article);
    }
}
