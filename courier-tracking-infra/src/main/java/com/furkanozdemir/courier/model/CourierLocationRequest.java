package com.furkanozdemir.courier.model;

import java.time.LocalDateTime;

public record CourierLocationRequest(String courierId, Double latitude, Double longitude, LocalDateTime time) {

}
