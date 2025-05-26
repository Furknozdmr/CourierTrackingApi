package com.furkanozdemir.store.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furkanozdemir.store.entity.StoreEntity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class StoreRepository {

    private final Set<StoreEntity> storeEntities;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public StoreRepository() {
        this.storeEntities = loadStoresFromJsonFile();
    }

    public Set<StoreEntity> getStoreEntities() {
        return Collections.unmodifiableSet(storeEntities);
    }

    private Set<StoreEntity> loadStoresFromJsonFile() {
        try (InputStream inputStream = new ClassPathResource("stores.json").getInputStream()) {
            List<StoreEntity> stores = objectMapper.readValue(inputStream, new TypeReference<>() {
            });
            return new HashSet<>(stores);
        } catch (IOException e) {
            throw new RuntimeException("Error loading stores from Json", e);
        }
    }
}

