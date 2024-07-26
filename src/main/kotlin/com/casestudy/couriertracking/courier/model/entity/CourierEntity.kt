package com.casestudy.couriertracking.courier.model.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "couriers")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
data class CourierEntity (

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        val id: String = UUID.randomUUID().toString(),

        val courierId: String,

        val lat: Double,

        val lng: Double,

        val timestamp: LocalDateTime

)