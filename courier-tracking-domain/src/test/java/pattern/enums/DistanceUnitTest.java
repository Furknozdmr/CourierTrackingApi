package pattern.enums;

import com.furkanozdemir.pattern.enums.DistanceUnit;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceUnitTest {

    @Test
    void shouldHaveCorrectConversionFactors() {
        assertThat(DistanceUnit.M.getConversionFactor()).isEqualTo(1000.0);
        assertThat(DistanceUnit.KM.getConversionFactor()).isEqualTo(1.0);
        assertThat(DistanceUnit.MILE.getConversionFactor()).isEqualTo(0.621371);
    }

    @Test
    void fromString_shouldReturnCorrectOptionalOfDistanceUnit_whenValidString() {
        Optional<DistanceUnit> kmUnit = DistanceUnit.fromString("KM");
        assertThat(kmUnit).isPresent();
        assertThat(kmUnit.get()).isEqualTo(DistanceUnit.KM);

        Optional<DistanceUnit> mUnit = DistanceUnit.fromString("m");
        assertThat(mUnit).isPresent();
        assertThat(mUnit.get()).isEqualTo(DistanceUnit.M);

        Optional<DistanceUnit> mileUnit = DistanceUnit.fromString("Mile");
        assertThat(mileUnit).isPresent();
        assertThat(mileUnit.get()).isEqualTo(DistanceUnit.MILE);
    }

    @Test
    void fromString_shouldReturnEmptyOptional_whenNullString() {
        Optional<DistanceUnit> result = DistanceUnit.fromString(null);
        assertThat(result).isEmpty();
    }

    @Test
    void fromString_shouldReturnEmptyOptional_whenEmptyString() {
        Optional<DistanceUnit> result = DistanceUnit.fromString("");
        assertThat(result).isEmpty();
    }

    @Test
    void fromString_shouldReturnEmptyOptional_whenBlankString() {
        Optional<DistanceUnit> result = DistanceUnit.fromString("   ");
        assertThat(result).isEmpty();
    }

    @Test
    void fromString_shouldReturnEmptyOptional_whenInvalidString() {
        Optional<DistanceUnit> result = DistanceUnit.fromString("CM");
        assertThat(result).isEmpty();

        Optional<DistanceUnit> result2 = DistanceUnit.fromString("INVALID");
        assertThat(result2).isEmpty();
    }

    @Test
    void shouldReturnAllEnumValues() {
        DistanceUnit[] values = DistanceUnit.values();
        assertThat(values).containsExactlyInAnyOrder(DistanceUnit.M, DistanceUnit.KM, DistanceUnit.MILE);
        assertThat(values.length).isEqualTo(3);
    }
}