package com.furkanozdemir.store;

import com.furkanozdemir.store.model.Store;
import com.furkanozdemir.store.port.StorePort;
import com.furkanozdemir.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreDataAdapter implements StorePort {

    private final StoreRepository repository;

    @Override
    public Set<Store> getAllStore() {
        var storeEntities = repository.getStoreEntities();

        if (Objects.isNull(storeEntities) || storeEntities.isEmpty()) {
            return Collections.emptySet();
        }

        return storeEntities.stream().map(entity -> new Store(entity.name(), entity.lat(), entity.lng())).collect(Collectors.toSet());
    }
}

