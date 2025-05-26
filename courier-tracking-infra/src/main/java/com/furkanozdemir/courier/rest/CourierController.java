package com.furkanozdemir.courier.rest;

import com.furkanozdemir.common.UseCaseHandler;
import com.furkanozdemir.common.VoidUseCaseHandler;
import com.furkanozdemir.courier.model.CourierLocationRequest;
import com.furkanozdemir.courier.usecase.CourierSaveLogLocationUseCase;
import com.furkanozdemir.courier.usecase.CourierTotalDistanceUseCase;
import com.furkanozdemir.pattern.enums.DistanceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/courier")
public class CourierController {

    private final VoidUseCaseHandler<CourierSaveLogLocationUseCase> courierSaveLogLocationUseCaseVoidUseCaseHandler;

    private final UseCaseHandler<Double, CourierTotalDistanceUseCase> courierTotalDistanceUseCaseHandler;

    @PostMapping(path = "/log-instant-location")
    @ResponseStatus(code = HttpStatus.CREATED) //
    public ResponseEntity<Void> logInstantLocation(@RequestBody CourierLocationRequest request) {

        var useCase = new CourierSaveLogLocationUseCase(request.courierId(), request.latitude(), request.longitude(), request.time());

        courierSaveLogLocationUseCaseVoidUseCaseHandler.handle(useCase);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/{courierId}/totalDistance")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable String courierId, @RequestParam(defaultValue = "km") String distanceUnit) {
        DistanceUnit unit = DistanceUnit.fromString(distanceUnit)
                                        .orElseThrow(() -> new IllegalArgumentException("Invalid distance unit: " + distanceUnit));

        var useCase = new CourierTotalDistanceUseCase(courierId, unit);

        var distance = courierTotalDistanceUseCaseHandler.handle(useCase);
        return ResponseEntity.ok(distance);
    }
}