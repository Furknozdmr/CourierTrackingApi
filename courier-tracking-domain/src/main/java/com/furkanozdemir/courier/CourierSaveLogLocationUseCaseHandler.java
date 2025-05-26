package com.furkanozdemir.courier;

import com.furkanozdemir.common.DomainComponent;
import com.furkanozdemir.common.VoidUseCaseHandler;
import com.furkanozdemir.common.exception.CourierNearStoreException;
import com.furkanozdemir.common.exception.InvalidLocationException;
import com.furkanozdemir.courier.port.CourierLocationPort;
import com.furkanozdemir.courier.usecase.CourierSaveLogLocationUseCase;
import com.furkanozdemir.pattern.DistanceStrategyFactory;
import com.furkanozdemir.pattern.enums.DistanceUnit;
import com.furkanozdemir.store.port.StorePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.furkanozdemir.pattern.enums.DistanceStrategyType.HAVERSINE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@DomainComponent
@RequiredArgsConstructor
public class CourierSaveLogLocationUseCaseHandler implements VoidUseCaseHandler<CourierSaveLogLocationUseCase> {

    private static final double DISTANCE_THRESHOLD = 100;

    //Geographical borders of Turkey
    private static final double MIN_LATITUDE = 35.80;

    private static final double MAX_LATITUDE = 42.10;

    private static final double MIN_LONGITUDE = 25.75;

    private static final double MAX_LONGITUDE = 44.82;

    private final CourierLocationPort courierLocationPort;

    private final StorePort storePort;

    private final DistanceStrategyFactory distanceStrategyFactory;

    @Override
    public void handle(CourierSaveLogLocationUseCase useCase) {
        validateLocation(useCase.latitude(), useCase.longitude());
        var calculator = distanceStrategyFactory.create(HAVERSINE);
        LocalDateTime oneMinuteAgo = useCase.dateTime().minusMinutes(1);

        var nearStore = storePort.getAllStore().stream().filter(storeModel -> {
            double distance = calculator.calculateDistance(useCase.latitude(), useCase.longitude(), storeModel.lat(), storeModel.lng(),
                                                           DistanceUnit.M);

            return distance < DISTANCE_THRESHOLD;
        }).findFirst().orElse(null);
        if (nonNull(nearStore)) {
            boolean hasExistingEntry = courierLocationPort.timeRangeEntry(useCase.courierId(), nearStore.name(), oneMinuteAgo);

            if (hasExistingEntry) {
                throw new CourierNearStoreException(
                        String.format("Courier cannot operate in the specified area (lat: %.6f, lng: %.6f) within the last minute.",
                                      useCase.latitude(), useCase.longitude()));
            }
        }
        courierLocationPort.saveLogLocation(useCase, nonNull(nearStore) ? nearStore.name() : null);
    }

    private void validateLocation(Double latitude, Double longitude) {
        if (isNull(latitude) || isNull(longitude)) {
            throw new InvalidLocationException("Latitude or longitude cannot be null.");
        }

        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE || longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new InvalidLocationException(
                    String.format("Location (lat: %.6f, lng: %.6f) is outside of Turkey's operational area.", latitude, longitude));
        }
    }
}
