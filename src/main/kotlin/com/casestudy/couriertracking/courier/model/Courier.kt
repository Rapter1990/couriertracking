package com.casestudy.couriertracking.courier.model

import java.time.LocalDateTime

/**
 * Represents a courier with detailed information about their location and associated store.
 *
 * @property id the unique identifier of the courier.
 * @property courierId the identifier for the courier, typically used in application logic.
 * @property lat the latitude of the courier's location.
 * @property lng the longitude of the courier's location.
 * @property storeName the name of the store associated with the courier's current location.
 * @property timestamp the timestamp when the courier's location was recorded.
 */
data class Courier(
        val id: String,
        val courierId: String,
        val lat: Double,
        val lng: Double,
        val storeName: String,
        val timestamp: LocalDateTime
)