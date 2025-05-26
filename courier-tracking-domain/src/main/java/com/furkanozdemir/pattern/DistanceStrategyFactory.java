package com.furkanozdemir.pattern;

import com.furkanozdemir.common.DomainComponent;
import com.furkanozdemir.pattern.enums.DistanceStrategyType;

@DomainComponent
public class DistanceStrategyFactory {

    public static DistanceCalculator create(DistanceStrategyType type) {
        return switch (type) {
            case HAVERSINE -> new HaversineDistanceStrategy();
        };
    }

}
