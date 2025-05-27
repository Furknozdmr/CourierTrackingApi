package store;

import com.furkanozdemir.common.exception.StoreListEmptyException;
import com.furkanozdemir.store.StoreGetAllResponseUseCaseHandler;
import com.furkanozdemir.store.model.Store;
import com.furkanozdemir.store.port.StorePort;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreGetAllResponseUseCaseHandlerTest {

    @InjectMocks
    private StoreGetAllResponseUseCaseHandler handler;

    @Mock
    private StorePort storePort;

    private Set<Store> mockStores;

    @BeforeEach
    void setUp() {
        mockStores = new HashSet<>();
        mockStores.add(new Store("Ataşehir Mağazası", 40.99, 29.15));
        mockStores.add(new Store("Kadıköy Mağazası", 40.98, 29.02));
    }

    @Test
    void shouldReturnAllStoresWhenListIsNotEmpty() {
        // GIVEN
        when(storePort.getAllStore()).thenReturn(mockStores);

        // WHEN
        Set<Store> result = handler.handle();

        // THEN
        assertThat(result).isNotNull().isNotEmpty().containsExactlyInAnyOrderElementsOf(mockStores);

        verify(storePort, times(1)).getAllStore();
    }

    @Test
    void shouldThrowStoreListEmptyExceptionWhenListIsEmpty() {
        // GIVEN
        when(storePort.getAllStore()).thenReturn(Collections.emptySet());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle()).isInstanceOf(StoreListEmptyException.class).hasNoCause();
        verify(storePort, times(1)).getAllStore();
    }

    @Test
    void shouldThrowStoreListEmptyExceptionWhenListIsNull() {
        // GIVEN
        when(storePort.getAllStore()).thenReturn(null);

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle()).isInstanceOf(StoreListEmptyException.class).hasNoCause();

        verify(storePort, times(1)).getAllStore();
    }
}