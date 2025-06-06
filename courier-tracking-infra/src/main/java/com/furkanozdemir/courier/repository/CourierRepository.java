package com.furkanozdemir.courier.repository;

import com.furkanozdemir.courier.entity.CourierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<CourierEntity, Long> {

    boolean existsByCourierIdAndStoreNameAndEntranceTimeIsGreaterThanEqual(String courierId, String storeName, LocalDateTime time);

    List<CourierEntity> findAllByCourierIdOrderByEntranceTimeAsc(String courierId);

}
