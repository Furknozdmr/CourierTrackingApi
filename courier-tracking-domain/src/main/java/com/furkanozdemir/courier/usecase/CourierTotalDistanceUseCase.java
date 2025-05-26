package com.furkanozdemir.courier.usecase;

import com.furkanozdemir.pattern.enums.DistanceUnit;

public record CourierTotalDistanceUseCase(String courierId, DistanceUnit distanceUnit) {

}
