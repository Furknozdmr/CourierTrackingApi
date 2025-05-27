package pattern.enums;

import com.furkanozdemir.pattern.enums.DistanceStrategyType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceStrategyTypeTest {

    @Test
    void shouldContainHaversineType() {
        assertThat(DistanceStrategyType.valueOf("HAVERSINE")).isEqualTo(DistanceStrategyType.HAVERSINE);
    }

    @Test
    void shouldHaveCorrectNameAndOrdinalForHaversine() {
        DistanceStrategyType haversine = DistanceStrategyType.HAVERSINE;
        assertThat(haversine.name()).isEqualTo("HAVERSINE");
        assertThat(haversine.ordinal()).isEqualTo(0);
    }

    @Test
    void shouldReturnAllEnumValues() {
        DistanceStrategyType[] values = DistanceStrategyType.values();
        assertThat(values).containsExactly(DistanceStrategyType.HAVERSINE);
        assertThat(values.length).isEqualTo(1);
    }
}