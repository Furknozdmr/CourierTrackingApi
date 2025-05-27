package store.entity;

import com.furkanozdemir.store.entity.StoreEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StoreEntityTest {

    @Test
    void shouldCreateStoreEntityCorrectly() {
        String name = "Test Store";
        Double lat = 40.0;
        Double lng = 30.0;

        StoreEntity storeEntity = new StoreEntity(name, lat, lng);

        assertThat(storeEntity.name()).isEqualTo(name);
        assertThat(storeEntity.lat()).isEqualTo(lat);
        assertThat(storeEntity.lng()).isEqualTo(lng);
    }

    @Test
    void equalsShouldReturnTrueForSameName() {
        StoreEntity store1 = new StoreEntity("Same Name", 40.0, 30.0);
        StoreEntity store2 = new StoreEntity("Same Name", 41.0, 31.0);

        assertThat(store1.equals(store2)).isTrue();
        assertThat(store1.hashCode()).isEqualTo(store2.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentName() {
        StoreEntity store1 = new StoreEntity("Store A", 40.0, 30.0);
        StoreEntity store2 = new StoreEntity("Store B", 40.0, 30.0);

        assertThat(store1.equals(store2)).isFalse();
        assertThat(store1.hashCode()).isNotEqualTo(store2.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForNullOrDifferentClass() {
        StoreEntity store = new StoreEntity("Test Store", 40.0, 30.0);

        assertThat(store.equals(null)).isFalse();
        assertThat(store.equals("A String")).isFalse();
    }

    @Test
    void equalsShouldReturnTrueForSameInstance() {
        StoreEntity store = new StoreEntity("Test Store", 40.0, 30.0);

        assertThat(store.equals(store)).isTrue();
    }
}