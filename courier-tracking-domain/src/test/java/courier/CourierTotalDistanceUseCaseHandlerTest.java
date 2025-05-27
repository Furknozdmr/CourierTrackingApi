package courier;

import com.furkanozdemir.courier.CourierTotalDistanceUseCaseHandler;
import com.furkanozdemir.courier.model.Courier;
import com.furkanozdemir.courier.port.CourierLocationPort;
import com.furkanozdemir.courier.usecase.CourierTotalDistanceUseCase;
import com.furkanozdemir.pattern.DistanceCalculator;
import com.furkanozdemir.pattern.enums.DistanceStrategyType;
import com.furkanozdemir.pattern.enums.DistanceUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import pattern.enums.DistanceStrategyFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierTotalDistanceUseCaseHandlerTest {

    @InjectMocks
    private CourierTotalDistanceUseCaseHandler handler;

    @Mock
    private CourierLocationPort courierLocationPort;

    @Mock
    private DistanceCalculator distanceCalculator;

    private MockedStatic<DistanceStrategyFactory> mockedStaticDistanceStrategyFactory;

    @BeforeEach
    void setUp() {
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
    void shouldReturnZeroWhenNoLocations() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.KM;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);

        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(Collections.emptyList());

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(0.0);
        verify(distanceCalculator, never()).calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(DistanceUnit.class));
    }

    @Test
    void shouldReturnZeroWhenSingleLocation() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.KM;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);
        List<Courier> locations = Collections.singletonList(new Courier(40.0, 30.0));
        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(locations);

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(0.0);
        verify(distanceCalculator, never()).calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(DistanceUnit.class));
    }

    @Test
    void shouldReturnZeroWhenLocationsIsNull() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.KM;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);
        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(null);

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(0.0);
        verify(distanceCalculator, never()).calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), any(DistanceUnit.class));
    }

    @Test
    void shouldCalculateDistanceForTwoLocations() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.KM;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);
        List<Courier> locations = Arrays.asList(new Courier(40.0, 30.0), new Courier(40.1, 30.1));
        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(locations);

        when(distanceCalculator.calculateDistance(eq(40.0), eq(30.0), eq(40.1), eq(30.1), eq(DistanceUnit.KM))).thenReturn(15.0);

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(15.0);
        verify(distanceCalculator, times(1)).calculateDistance(eq(40.0), eq(30.0), eq(40.1), eq(30.1), eq(DistanceUnit.KM));
    }

    @Test
    void shouldCalculateTotalDistanceForMultipleLocations() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.KM;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);
        List<Courier> locations = Arrays.asList(new Courier(40.0, 30.0), new Courier(40.1, 30.1), new Courier(40.2, 30.2));
        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(locations);

        when(distanceCalculator.calculateDistance(eq(40.0), eq(30.0), eq(40.1), eq(30.1), eq(DistanceUnit.KM))).thenReturn(15.0);

        when(distanceCalculator.calculateDistance(eq(40.1), eq(30.1), eq(40.2), eq(30.2), eq(DistanceUnit.KM))).thenReturn(20.0);

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(35.0);
        verify(distanceCalculator, times(1)).calculateDistance(eq(40.0), eq(30.0), eq(40.1), eq(30.1), eq(DistanceUnit.KM));
        verify(distanceCalculator, times(1)).calculateDistance(eq(40.1), eq(30.1), eq(40.2), eq(30.2), eq(DistanceUnit.KM));
    }

    @Test
    void shouldCalculateDistanceInMetersAndRoundCorrectly() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.M;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);
        List<Courier> locations = Arrays.asList(new Courier(40.0, 30.0), new Courier(40.0001, 30.0001));
        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(locations);

        when(distanceCalculator.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.M))).thenReturn(15.123456789);

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(15.12);
        verify(distanceCalculator, times(1)).calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.M));
    }

    @Test
    void shouldCalculateDistanceInMilesAndRoundCorrectly() {
        // GIVEN
        String courierId = "courier1";
        DistanceUnit unit = DistanceUnit.MILE;
        CourierTotalDistanceUseCase useCase = new CourierTotalDistanceUseCase(courierId, unit);
        List<Courier> locations = Arrays.asList(new Courier(40.0, 30.0), new Courier(40.1, 30.1));
        when(courierLocationPort.getLocationsByCourierId(courierId)).thenReturn(locations);

        when(distanceCalculator.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.MILE))).thenReturn(9.387654321);

        // WHEN
        Double totalDistance = handler.handle(useCase);

        // THEN
        assertThat(totalDistance).isEqualTo(9.39);
        verify(distanceCalculator, times(1)).calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(DistanceUnit.MILE));
    }
}