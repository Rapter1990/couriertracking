package com.casestudy.couriertracking.model

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
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: UUID = UUID.randomUUID(),
        val lat: Double,
        val lng: Double,
        val timestamp: LocalDateTime

)