package com.furkanozdemir.courier.usecase;

import com.furkanozdemir.common.UseCase;

import java.time.LocalDateTime;

public record CourierSaveLogLocationUseCase(String courierId, Double latitude, Double longitude, LocalDateTime dateTime) implements UseCase {

}
