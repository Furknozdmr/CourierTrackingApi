package com.furkanozdemir.pattern.enums;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum DistanceUnit {
    M(1000),
    KM(1),
    MILE(0.621371);

    private final double conversionFactor;

    DistanceUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public static Optional<DistanceUnit> fromString(String distanceUnit) {
        if (distanceUnit == null || distanceUnit.trim().isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(DistanceUnit.valueOf(distanceUnit.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}