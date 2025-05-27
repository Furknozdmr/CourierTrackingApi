package com.furkanozdemir.courier;

import com.furkanozdemir.common.DomainComponent;
import com.furkanozdemir.common.UseCaseHandler;
import com.furkanozdemir.courier.model.Courier;
import com.furkanozdemir.courier.port.CourierLocationPort;
import com.furkanozdemir.courier.usecase.CourierTotalDistanceUseCase;
import com.furkanozdemir.pattern.enums.DistanceUnit;
import lombok.RequiredArgsConstructor;
import pattern.enums.DistanceStrategyFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.IntStream;

import static com.furkanozdemir.pattern.enums.DistanceStrategyType.HAVERSINE;

@DomainComponent
@RequiredArgsConstructor
public class CourierTotalDistanceUseCaseHandler implements UseCaseHandler<Double, CourierTotalDistanceUseCase> {

    private final CourierLocationPort courierLocationPort;

    @Override
    public Double handle(CourierTotalDistanceUseCase useCase) {
        var locations = courierLocationPort.getLocationsByCourierId(useCase.courierId());
        return getTotalTravelDistance(locations, useCase.distanceUnit());
    }

    public double getTotalTravelDistance(List<Courier> locations, DistanceUnit distanceUnit) {
        if (locations == null || locations.size() < 2) {
            return 0.0;
        }

        var calculator = DistanceStrategyFactory.create(HAVERSINE);

        double totalDistance = IntStream.range(0, locations.size() - 1).mapToDouble(i -> {
            Courier prev = locations.get(i);
            Courier current = locations.get(i + 1);
            return calculator.calculateDistance(prev.lat(), prev.lon(), current.lat(), current.lon(), distanceUnit);
        }).sum();
        return BigDecimal.valueOf(totalDistance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
