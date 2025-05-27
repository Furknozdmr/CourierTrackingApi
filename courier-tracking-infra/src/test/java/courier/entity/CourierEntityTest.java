package courier.entity;

import com.furkanozdemir.courier.entity.CourierEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CourierEntityTest {

    @Test
    void shouldCreateCourierEntityWithBuilder() {
        // GIVEN
        String courierId = "C123";
        Double lat = 40.7128;
        Double lon = -74.0060;
        String storeName = "Central Park Store";
        LocalDateTime entranceTime = LocalDateTime.of(2023, 1, 15, 10, 30);

        // WHEN
        CourierEntity courierEntity = CourierEntity.builder()
                                                   .courierId(courierId)
                                                   .lat(lat)
                                                   .lon(lon)
                                                   .storeName(storeName)
                                                   .entranceTime(entranceTime)
                                                   .build();

        // THEN
        assertThat(courierEntity).isNotNull();
        assertThat(courierEntity.getCourierId()).isEqualTo(courierId);
        assertThat(courierEntity.getLat()).isEqualTo(lat);
        assertThat(courierEntity.getLon()).isEqualTo(lon);
        assertThat(courierEntity.getStoreName()).isEqualTo(storeName);
        assertThat(courierEntity.getEntranceTime()).isEqualTo(entranceTime);
    }

    @Test
    void shouldSetAndGetValuesCorrectly() {
        // GIVEN
        CourierEntity courierEntity = new CourierEntity();

        String newCourierId = "C456";
        Double newLat = 34.0522;
        Double newLon = -118.2437;
        String newStoreName = "Hollywood Store";
        LocalDateTime newEntranceTime = LocalDateTime.of(2024, 5, 20, 14, 0);

        // WHEN
        courierEntity.setCourierId(newCourierId);
        courierEntity.setLat(newLat);
        courierEntity.setLon(newLon);
        courierEntity.setStoreName(newStoreName);
        courierEntity.setEntranceTime(newEntranceTime);

        // THEN
        assertThat(courierEntity.getCourierId()).isEqualTo(newCourierId);
        assertThat(courierEntity.getLat()).isEqualTo(newLat);
        assertThat(courierEntity.getLon()).isEqualTo(newLon);
        assertThat(courierEntity.getStoreName()).isEqualTo(newStoreName);
        assertThat(courierEntity.getEntranceTime()).isEqualTo(newEntranceTime);
    }

    @Test
    void shouldWorkWithAllArgsConstructor() {
        // GIVEN
        String courierId = "C789";
        Double lat = 39.9042;
        Double lon = 116.4074;
        String storeName = "Beijing Store";
        LocalDateTime entranceTime = LocalDateTime.of(2023, 11, 1, 9, 0);

        // WHEN
        CourierEntity courierEntity = new CourierEntity(courierId, lat, lon, storeName, entranceTime);

        // THEN
        assertThat(courierEntity.getCourierId()).isEqualTo(courierId);
        assertThat(courierEntity.getLat()).isEqualTo(lat);
        assertThat(courierEntity.getLon()).isEqualTo(lon);
        assertThat(courierEntity.getStoreName()).isEqualTo(storeName);
        assertThat(courierEntity.getEntranceTime()).isEqualTo(entranceTime);
    }
}
