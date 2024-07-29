package com.casestudy.couriertracking.courier.model.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import java.util.*

/**
 * Entity representing a store location.
 *
 * @property id the unique identifier of the store record, automatically generated as a UUID.
 * @property name the name of the store.
 * @property lat the latitude of the store's location.
 * @property lng the longitude of the store's location.
 * @property createdAt the timestamp when the store record was created, automatically set to the current time if not specified.
 * @constructor Creates a `StoreEntity` with the specified properties.
 */
@Entity
@Table(name = "stores")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
data class StoreEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        val id: String = UUID.randomUUID().toString(),

        val name: String,

        val lat: Double,

        val lng: Double,

        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now()

)