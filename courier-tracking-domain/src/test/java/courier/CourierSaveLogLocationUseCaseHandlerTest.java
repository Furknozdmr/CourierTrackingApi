package courier;

import com.furkanozdemir.common.exception.CourierNearStoreException;
import com.furkanozdemir.common.exception.InvalidLocationException;
import com.furkanozdemir.courier.CourierSaveLogLocationUseCaseHandler;
import com.furkanozdemir.courier.port.CourierLocationPort;
import com.furkanozdemir.courier.usecase.CourierSaveLogLocationUseCase;
import com.furkanozdemir.pattern.DistanceCalculator;
import com.furkanozdemir.pattern.enums.DistanceStrategyType;
import com.furkanozdemir.pattern.enums.DistanceUnit;
import com.furkanozdemir.store.model.Store;
import com.furkanozdemir.store.port.StorePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.enums.DistanceStrategyFactory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierSaveLogLocationUseCaseHandlerTest {

    @InjectMocks
    private CourierSaveLogLocationUseCaseHandler handler;

    @Mock
    private CourierLocationPort courierLocationPort;

    @Mock
    private StorePort storePort;

    @Mock
    private DistanceCalculator distanceCalculator;

    private CourierSaveLogLocationUseCase useCase;

    private Store mockStore;

    private Set<Store> mockStores;

    private MockedStatic<DistanceStrategyFactory> mockedStaticDistanceStrategyFactory;

    @BeforeEach
    void setUp() {
        useCase = new CourierSaveLogLocationUseCase("courier123", 40.999, 29.001, LocalDateTime.now());

        mockStore = new Store("MockStoreA", 41.000, 29.000);
        mockStores = new HashSet<>();
        mockStores.add(mockStore);

        mockedStaticDistanceStrategyFactory = mockStatic(DistanceStrategyFactory.class);

        mockedStaticDistanceStrategyFactory.when(() -> DistanceStrategyFactory.create(any(DistanceStrategyType.class)))
                                           .thenReturn(distanceCalculator);
    }

    @AfterEach
    void tearDown() {
        if (mockedStaticDistanceStrategyFactory != null) {
            mockedStaticDistanceStrategyFactory.close();
        }
    }

    @Test
    void shouldSaveLogLocationWhenNearStoreAndNoExistingEntry() {
        // GIVEN
        when(storePort.getAllStore()).thenReturn(mockStores);

        when(distanceCalculator.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.M))).thenReturn(50.0);

        when(courierLocationPort.timeRangeEntry(useCase.courierId(), mockStore.name(), useCase.dateTime().minusMinutes(1))).thenReturn(false);

        // WHEN
        handler.handle(useCase);

        // THEN
        verify(courierLocationPort, times(1)).saveLogLocation(useCase, mockStore.name());
    }

    @Test
    void shouldThrowCourierNearStoreExceptionWhenNearStoreAndHasExistingEntry() {
        // GIVEN
        when(storePort.getAllStore()).thenReturn(mockStores);

        when(distanceCalculator.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.M))).thenReturn(50.0);

        when(courierLocationPort.timeRangeEntry(useCase.courierId(), mockStore.name(), useCase.dateTime().minusMinutes(1))).thenReturn(true);

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(CourierNearStoreException.class)
                                                         .hasMessageContaining("Courier cannot operate in the specified area");

        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldThrowInvalidLocationExceptionWhenLatitudeIsTooLow() {
        // GIVEN
        useCase = new CourierSaveLogLocationUseCase("courier123", 30.0, 30.0, LocalDateTime.now());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(InvalidLocationException.class)
                                                         .hasMessageContaining(
                                                                 "Location (lat: 30.000000, lng: 30.000000) is outside of Turkey's operational area.");

        verify(storePort, never()).getAllStore();
        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldThrowInvalidLocationExceptionWhenLatitudeIsTooHigh() {
        // GIVEN
        useCase = new CourierSaveLogLocationUseCase("courier123", 45.0, 30.0, LocalDateTime.now());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(InvalidLocationException.class)
                                                         .hasMessageContaining(
                                                                 "Location (lat: 45.000000, lng: 30.000000) is outside of Turkey's operational area.");

        verify(storePort, never()).getAllStore();
        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldThrowInvalidLocationExceptionWhenLongitudeIsTooLow() {
        // GIVEN
        useCase = new CourierSaveLogLocationUseCase("courier123", 40.0, 20.0, LocalDateTime.now());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(InvalidLocationException.class)
                                                         .hasMessageContaining(
                                                                 "Location (lat: 40.000000, lng: 20.000000) is outside of Turkey's operational area.");

        verify(storePort, never()).getAllStore();
        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldThrowInvalidLocationExceptionWhenLongitudeIsTooHigh() {
        // GIVEN
        useCase = new CourierSaveLogLocationUseCase("courier123", 40.0, 48.0, LocalDateTime.now());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(InvalidLocationException.class)
                                                         .hasMessageContaining(
                                                                 "Location (lat: 40.000000, lng: 48.000000) is outside of Turkey's operational area.");

        verify(storePort, never()).getAllStore();
        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldThrowInvalidLocationExceptionWhenLatitudeIsNull() {
        // GIVEN
        useCase = new CourierSaveLogLocationUseCase("courier123", null, 29.001, LocalDateTime.now());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(InvalidLocationException.class)
                                                         .hasMessageContaining("Latitude or longitude cannot be null.");

        verify(storePort, never()).getAllStore();
        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldThrowInvalidLocationExceptionWhenLongitudeIsNull() {
        // GIVEN
        useCase = new CourierSaveLogLocationUseCase("courier123", 40.999, null, LocalDateTime.now());

        // WHEN & THEN
        assertThatThrownBy(() -> handler.handle(useCase)).isInstanceOf(InvalidLocationException.class)
                                                         .hasMessageContaining("Latitude or longitude cannot be null.");

        verify(storePort, never()).getAllStore();
        verify(courierLocationPort, never()).saveLogLocation(any(), anyString());
    }

    @Test
    void shouldSaveLogLocationWhenNotInRangeOfAnyStore() {
        // GIVEN
        when(storePort.getAllStore()).thenReturn(mockStores);

        when(distanceCalculator.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.M))).thenReturn(
                200.0);

        // WHEN
        handler.handle(useCase);

        // THEN
        verify(courierLocationPort, times(1)).saveLogLocation(useCase, null);
    }

    @Test
    void shouldSaveLogLocationWhenNearOneStoreAndNotLoggedPreviously() {
        // GIVEN
        Store farStore = new Store("MockStoreB", 41.500, 29.500);
        mockStores.add(farStore);
        when(storePort.getAllStore()).thenReturn(mockStores);

        when(distanceCalculator.calculateDistance(useCase.latitude(), useCase.longitude(), mockStore.lat(), mockStore.lng(),
                                                  DistanceUnit.M)).thenReturn(50.0);

        when(distanceCalculator.calculateDistance(useCase.latitude(), useCase.longitude(), farStore.lat(), farStore.lng(),
                                                  DistanceUnit.M)).thenReturn(5000.0);

        when(courierLocationPort.timeRangeEntry(useCase.courierId(), mockStore.name(), useCase.dateTime().minusMinutes(1))).thenReturn(false);

        // WHEN
        handler.handle(useCase);

        // THEN
        verify(courierLocationPort, times(1)).saveLogLocation(useCase, mockStore.name());
    }
}