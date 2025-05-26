package com.furkanozdemir.courier.entity;

import com.furkanozdemir.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "courier-location")
public class CourierEntity extends BaseEntity {

    private String courierId;

    private Double lat;

    private Double lon;

    private String storeName;

    private LocalDateTime entranceTime;
}
