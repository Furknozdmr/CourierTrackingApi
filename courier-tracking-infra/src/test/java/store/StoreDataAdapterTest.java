package store;

import com.furkanozdemir.store.StoreDataAdapter;
import com.furkanozdemir.store.entity.StoreEntity;
import com.furkanozdemir.store.model.Store;
import com.furkanozdemir.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreDataAdapterTest {

    @InjectMocks
    private StoreDataAdapter storeDataAdapter;

    @Mock
    private StoreRepository storeRepository;

    private Set<StoreEntity> mockStoreEntities;

    @BeforeEach
    void setUp() {
        mockStoreEntities = new HashSet<>();
        mockStoreEntities.add(new StoreEntity("Store A", 40.7128, -74.0060));
        mockStoreEntities.add(new StoreEntity("Store B", 34.0522, -118.2437));
    }

    @Test
    void shouldReturnAllStoresWhenEntitiesExist() {
        // GIVEN
        when(storeRepository.getStoreEntities()).thenReturn(mockStoreEntities);

        // WHEN
        Set<Store> result = storeDataAdapter.getAllStore();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(mockStoreEntities.size());

        Set<Store> expectedStores = new HashSet<>();
        for (StoreEntity entity : mockStoreEntities) {
            expectedStores.add(new Store(entity.name(), entity.lat(), entity.lng()));
        }
        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedStores);

        verify(storeRepository, times(1)).getStoreEntities();
    }

    @Test
    void shouldReturnEmptySetWhenNoEntitiesExist() {
        // GIVEN
        when(storeRepository.getStoreEntities()).thenReturn(Collections.emptySet());

        // WHEN
        Set<Store> result = storeDataAdapter.getAllStore();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(storeRepository, times(1)).getStoreEntities();
    }

    @Test
    void shouldReturnEmptySetWhenEntitiesAreNull() {
        // GIVEN
        when(storeRepository.getStoreEntities()).thenReturn(null);

        // WHEN
        Set<Store> result = storeDataAdapter.getAllStore();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();

        verify(storeRepository, times(1)).getStoreEntities();
    }
}