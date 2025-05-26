package com.furkanozdemir.pattern;

import com.furkanozdemir.pattern.enums.DistanceUnit;

public interface DistanceCalculator {

    double calculateDistance(double lat1, double lng1, double lat2, double lng2, DistanceUnit distanceUnit);
}
