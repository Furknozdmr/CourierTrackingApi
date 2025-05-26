package com.furkanozdemir.courier.port;

import com.furkanozdemir.courier.model.Courier;
import com.furkanozdemir.courier.usecase.CourierSaveLogLocationUseCase;

import java.time.LocalDateTime;
import java.util.List;

public interface CourierLocationPort {

    void saveLogLocation(CourierSaveLogLocationUseCase courierSaveLogLocationUseCase, String storeName);

    boolean timeRangeEntry(String courierId, String name, LocalDateTime time);

    List<Courier> getLocationsByCourierId(String courierId);

}
