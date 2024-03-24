package com.modiconme.realworld.core.data.var1;

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
