package com.furkanozdemir.pattern;

import com.furkanozdemir.pattern.enums.DistanceUnit;

import static com.furkanozdemir.pattern.enums.DistanceUnit.KM;
import static com.furkanozdemir.pattern.enums.DistanceUnit.M;
import static com.furkanozdemir.pattern.enums.DistanceUnit.MILE;

public class HaversineDistanceStrategy implements DistanceCalculator {

    private static final double RADIUS = 6371;

    @Override
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2, DistanceUnit distanceUnit) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS * c * getRadius(distanceUnit);
    }

    private double getRadius(DistanceUnit distanceUnit) {
        switch (distanceUnit) {
            case KM:
                return KM.getConversionFactor();
            case M:
                return M.getConversionFactor();
            case MILE:
                return MILE.getConversionFactor();
            default:
                throw new IllegalArgumentException("Invalid distance unit: " + distanceUnit);
        }

    }
}
