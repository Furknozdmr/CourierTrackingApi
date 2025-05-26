package com.furkanozdemir.courier;

import com.furkanozdemir.courier.entity.CourierEntity;
import com.furkanozdemir.courier.model.Courier;
import com.furkanozdemir.courier.port.CourierLocationPort;
import com.furkanozdemir.courier.repository.CourierRepository;
import com.furkanozdemir.courier.usecase.CourierSaveLogLocationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourierDataAdapter implements CourierLocationPort {

    private final CourierRepository repository;

    @Override
    public void saveLogLocation(CourierSaveLogLocationUseCase useCase, String storeName) {

        var entity = getCourierEntity(useCase, storeName);

        repository.save(entity);
    }

    @Override
    public boolean timeRangeEntry(String courierId, String name, LocalDateTime time) {

        return repository.existsByCourierIdAndStoreNameAndEntranceTimeIsGreaterThanEqual(courierId, name, time);
    }

    @Override
    public List<Courier> getLocationsByCourierId(String courierId) {
        var locations = repository.findAllByCourierIdOrderByEntranceTimeAsc(courierId);
        return locations.stream().map(l -> new Courier(l.getLat(), l.getLon())).collect(Collectors.toList());
    }

    private CourierEntity getCourierEntity(CourierSaveLogLocationUseCase useCase, String storeName) {

        var courierEntity = new CourierEntity();
        courierEntity.setCourierId(useCase.courierId());
        courierEntity.setLat(useCase.latitude());
        courierEntity.setLon(useCase.longitude());
        courierEntity.setStoreName(storeName);
        courierEntity.setEntranceTime(useCase.dateTime());

        return courierEntity;
    }
}
