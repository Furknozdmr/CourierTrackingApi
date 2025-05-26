package com.furkanozdemir.store;

import com.furkanozdemir.common.DomainComponent;
import com.furkanozdemir.common.VoidResponseUseCaseHandler;
import com.furkanozdemir.common.exception.StoreListEmptyException;
import com.furkanozdemir.store.model.Store;
import com.furkanozdemir.store.port.StorePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@DomainComponent
@RequiredArgsConstructor
public class StoreGetAllResponseUseCaseHandler implements VoidResponseUseCaseHandler<Set<Store>> {

    private final StorePort storePort;

    @Override
    public Set<Store> handle() {
        Set<Store> storesList = storePort.getAllStore();

        return Optional.ofNullable(storesList)
                       .filter(list -> !list.isEmpty())
                       .map(list -> Set.copyOf(list))
                       .orElseThrow(StoreListEmptyException::new);
    }
}
