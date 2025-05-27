package courier;

import com.furkanozdemir.courier.CourierDataAdapter;
import com.furkanozdemir.courier.entity.CourierEntity;
import com.furkanozdemir.courier.model.Courier;
import com.furkanozdemir.courier.repository.CourierRepository;
import com.furkanozdemir.courier.usecase.CourierSaveLogLocationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierDataAdapterTest {

    @InjectMocks
    private CourierDataAdapter courierDataAdapter;

    @Mock
    private CourierRepository courierRepository;

    @Test
    void shouldSaveLogLocationSuccessfully() {
        // GIVEN
        String courierId = "C123";
        double lat = 40.0;
        double lon = 30.0;
        LocalDateTime dateTime = LocalDateTime.now();
        String storeName = "TestStore";

        CourierSaveLogLocationUseCase useCase = new CourierSaveLogLocationUseCase(courierId, lat, lon, dateTime);

        // WHEN
        courierDataAdapter.saveLogLocation(useCase, storeName);

        // THEN
        verify(courierRepository, times(1)).save(any(CourierEntity.class));

        ArgumentCaptor<CourierEntity> entityCaptor = ArgumentCaptor.forClass(CourierEntity.class);
        verify(courierRepository).save(entityCaptor.capture());
        CourierEntity capturedEntity = entityCaptor.getValue();
        assertThat(capturedEntity.getCourierId()).isEqualTo(courierId);
        assertThat(capturedEntity.getLat()).isEqualTo(lat);
        assertThat(capturedEntity.getLon()).isEqualTo(lon);
        assertThat(capturedEntity.getStoreName()).isEqualTo(storeName);
        assertThat(capturedEntity.getEntranceTime()).isEqualTo(dateTime);
    }

    @Test
    void shouldReturnTrueWhenEntryExistsInTimeRange() {
        // GIVEN
        String courierId = "C123";
        String storeName = "TestStore";
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(1);

        when(courierRepository.existsByCourierIdAndStoreNameAndEntranceTimeIsGreaterThanEqual(eq(courierId), eq(storeName),
                                                                                              eq(timeLimit))).thenReturn(true);

        // WHEN
        boolean exists = courierDataAdapter.timeRangeEntry(courierId, storeName, timeLimit);

        // THEN
        assertThat(exists).isTrue();
        verify(courierRepository, times(1)).existsByCourierIdAndStoreNameAndEntranceTimeIsGreaterThanEqual(eq(courierId), eq(storeName),
                                                                                                           eq(timeLimit));
    }

    @Test
    void shouldReturnFalseWhenNoEntryExistsInTimeRange() {
        // GIVEN
        String courierId = "C456";
        String storeName = "OtherStore";
        LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(5);

        when(courierRepository.existsByCourierIdAndStoreNameAndEntranceTimeIsGreaterThanEqual(eq(courierId), eq(storeName),
                                                                                              eq(timeLimit))).thenReturn(false);

        // WHEN
        boolean exists = courierDataAdapter.timeRangeEntry(courierId, storeName, timeLimit);

        // THEN
        assertThat(exists).isFalse();
        verify(courierRepository, times(1)).existsByCourierIdAndStoreNameAndEntranceTimeIsGreaterThanEqual(eq(courierId), eq(storeName),
                                                                                                           eq(timeLimit));
    }

    @Test
    void shouldReturnCourierLocationsSuccessfully() {
        // GIVEN
        String courierId = "C789";
        CourierEntity entity1 = CourierEntity.builder()
                                             .courierId(courierId)
                                             .lat(41.0)
                                             .lon(29.0)
                                             .entranceTime(LocalDateTime.now().minusHours(2))
                                             .build();
        CourierEntity entity2 = CourierEntity.builder()
                                             .courierId(courierId)
                                             .lat(41.1)
                                             .lon(29.1)
                                             .entranceTime(LocalDateTime.now().minusHours(1))
                                             .build();
        List<CourierEntity> entityList = Arrays.asList(entity1, entity2);

        when(courierRepository.findAllByCourierIdOrderByEntranceTimeAsc(eq(courierId))).thenReturn(entityList);

        // WHEN
        List<Courier> result = courierDataAdapter.getLocationsByCourierId(courierId);

        // THEN
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.size()).isEqualTo(2);

        assertThat(result.get(0).lat()).isEqualTo(entity1.getLat());
        assertThat(result.get(0).lon()).isEqualTo(entity1.getLon());
        assertThat(result.get(1).lat()).isEqualTo(entity2.getLat());
        assertThat(result.get(1).lon()).isEqualTo(entity2.getLon());

        verify(courierRepository, times(1)).findAllByCourierIdOrderByEntranceTimeAsc(eq(courierId));
    }

    @Test
    void shouldReturnEmptyListWhenNoLocationsForCourier() {
        // GIVEN
        String courierId = "C000";

        when(courierRepository.findAllByCourierIdOrderByEntranceTimeAsc(eq(courierId))).thenReturn(Collections.emptyList());

        // WHEN
        List<Courier> result = courierDataAdapter.getLocationsByCourierId(courierId);

        // THEN
        assertThat(result).isNotNull().isEmpty();
        verify(courierRepository, times(1)).findAllByCourierIdOrderByEntranceTimeAsc(eq(courierId));
    }
}