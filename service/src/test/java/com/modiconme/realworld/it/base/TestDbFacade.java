package com.modiconme.realworld.it.base;

import com.modiconme.realworld.it.base.repository.ArticleTagTestRepository;
import com.modiconme.realworld.it.base.repository.ArticleTestRepository;
import com.modiconme.realworld.it.base.repository.TagTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDbFacade {

    private final TestEntityManager testEntityManager;
    private final TransactionTemplate transactionTemplate;

    public final ArticleTestRepository articles;
    public final ArticleTagTestRepository articleTags;
    public final TagTestRepository tags;

    public <T> T findById(Class<T> clazz, Long id) {
        return transactionTemplate.execute(status -> testEntityManager.find(clazz, id));
    }

    public <T> List<T> persisted(T... entities) {
        List<T> list = Arrays.asList(entities);
        list.forEach(entity -> transactionTemplate.execute(
                status -> testEntityManager.persistAndFlush(entity)));
        return list;
    }

    public <T> T persisted(T entity) {
        return transactionTemplate.execute((status -> testEntityManager.persistAndFlush(entity)));
    }
}
