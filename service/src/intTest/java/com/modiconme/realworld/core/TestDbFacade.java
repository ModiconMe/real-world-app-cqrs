package com.modiconme.realworld.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;

public class TestDbFacade {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public <T> List<T> persisted(T... entities) {
        List<T> list = Arrays.asList(entities);
        list.forEach(entity -> transactionTemplate.execute(
                status -> testEntityManager.persistAndFlush(entity)));
        return list;
    }

    public <T> T persisted(T entity) {
        return transactionTemplate.execute(status -> {
            testEntityManager.persistAndFlush(entity);
            return entity;
        });
    }

    public <T> TestDataBuilder<T> persisted(TestDataBuilder<T> builder) {
        return () -> transactionTemplate.execute(status -> {
            final var entity = builder.build();
            testEntityManager.persistAndFlush(entity);
            return entity;
        });
    }

    @TestConfiguration
    public static class Config {

        @Bean
        public TestDbFacade testDbFacade() {
            return new TestDbFacade();
        }
    }
}
