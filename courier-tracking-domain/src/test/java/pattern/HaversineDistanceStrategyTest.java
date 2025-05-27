package pattern;

import com.furkanozdemir.pattern.HaversineDistanceStrategy;
import com.furkanozdemir.pattern.enums.DistanceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HaversineDistanceStrategyTest {

    private HaversineDistanceStrategy haversineCalculator;

    @BeforeEach
    void setUp() {
        haversineCalculator = new HaversineDistanceStrategy();
    }

    @Test
    void shouldReturnZeroWhenStartAndEndPointsAreSame() {
        double lat1 = 40.0;
        double lng1 = 30.0;
        double lat2 = 40.0;
        double lng2 = 30.0;

        double distanceKm = haversineCalculator.calculateDistance(lat1, lng1, lat2, lng2, DistanceUnit.KM);
        assertThat(distanceKm).isEqualTo(0.0);

        double distanceM = haversineCalculator.calculateDistance(lat1, lng1, lat2, lng2, DistanceUnit.M);
        assertThat(distanceM).isEqualTo(0.0);

        double distanceMile = haversineCalculator.calculateDistance(lat1, lng1, lat2, lng2, DistanceUnit.MILE);
        assertThat(distanceMile).isEqualTo(0.0);
    }

    @Test
    void shouldCalculateDistanceAcrossPrimeMeridian() {
        double lat1 = 51.5074;
        double lng1 = 0.1278;
        double lat2 = 48.8566;
        double lng2 = 2.3522;

        double expectedDistanceKm = 334.58;

        double actualDistanceKm = haversineCalculator.calculateDistance(lat1, lng1, lat2, lng2, DistanceUnit.KM);

        assertThat(actualDistanceKm).isCloseTo(expectedDistanceKm, org.assertj.core.api.Assertions.offset(0.01));
    }
}