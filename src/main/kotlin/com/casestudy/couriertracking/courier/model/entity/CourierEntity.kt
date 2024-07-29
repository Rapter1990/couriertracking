package com.casestudy.couriertracking.courier.model.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import java.util.*

/**
 * Entity representing a courier's travel record.
 *
 * @property id the unique identifier of the courier record, automatically generated as a UUID.
 * @property courierId the unique identifier of the courier.
 * @property lat the latitude of the courier's location.
 * @property lng the longitude of the courier's location.
 * @property storeName the name of the store associated with the courier's travel.
 * @property timestamp the timestamp of when the courier was at the specified location, formatted as "dd/MM/yyyy HH:mm".
 * @constructor Creates a `CourierEntity` with the specified properties.
 */
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

        @Column(name = "store_name", nullable = false)
        val storeName: String,

        val timestamp: LocalDateTime

)