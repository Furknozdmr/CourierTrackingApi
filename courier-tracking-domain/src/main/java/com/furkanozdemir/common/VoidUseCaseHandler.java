package com.furkanozdemir.common;

@DomainComponent
public interface VoidUseCaseHandler<T extends UseCase> {

    void handle(T useCase);

}
