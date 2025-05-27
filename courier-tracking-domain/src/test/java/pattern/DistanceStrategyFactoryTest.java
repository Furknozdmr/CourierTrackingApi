package pattern;

import com.furkanozdemir.pattern.DistanceCalculator;
import com.furkanozdemir.pattern.HaversineDistanceStrategy;
import com.furkanozdemir.pattern.enums.DistanceStrategyType;
import org.junit.jupiter.api.Test;
import pattern.enums.DistanceStrategyFactory;

import static com.furkanozdemir.pattern.enums.DistanceStrategyType.HAVERSINE;
import static org.assertj.core.api.Assertions.assertThat;

class DistanceStrategyFactoryTest {

    @Test
    void shouldReturnHaversineDistanceStrategyWhenHaversineTypeIsGiven() {
        // GIVEN
        DistanceStrategyType type = HAVERSINE;

        // WHEN
        DistanceCalculator calculator = DistanceStrategyFactory.create(type);

        // THEN
        assertThat(calculator).isInstanceOf(HaversineDistanceStrategy.class);
        assertThat(calculator).isNotNull();
    }
}