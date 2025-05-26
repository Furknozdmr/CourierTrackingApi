package com.furkanozdemir.common;

public interface UseCaseHandler<E, T> {

    E handle(T useCase);

}
