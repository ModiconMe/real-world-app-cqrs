package com.modiconme.realworld.cqrs;

public interface QueryHandler<R, Q extends Query<R>> {

    R handle(Q query);

}
