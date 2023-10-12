package com.modiconme.realworld.cqrs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultBus implements Bus {

    private final Registry registry;

    @SuppressWarnings("unchecked")
    @Override
    public <R, C extends Command<R>> R executeCommand(C command) {
        CommandHandler<R, C> commandHandler = registry.getCommandHandler(command.getClass());
        return commandHandler.handle(command);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R, Q extends Query<R>> R executeQuery(Q query) {
        QueryHandler<R, Q> queryHandler = registry.getQueryHandler(query.getClass());
        return queryHandler.handle(query);
    }

}
