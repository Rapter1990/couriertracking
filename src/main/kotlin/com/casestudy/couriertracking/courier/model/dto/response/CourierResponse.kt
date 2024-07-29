package com.casestudy.couriertracking.courier.model.dto.response

import java.time.LocalDateTime

/**
 * Data class representing the response for a courier's travel details.
 *
 * @property id the unique identifier of the courier.
 * @property lat the latitude of the courier's location.
 * @property lng the longitude of the courier's location.
 * @property storeName the name of the store associated with the courier's travel.
 * @property timestamp the timestamp of when the courier was at the specified location, formatted as "dd/MM/yyyy HH:mm".
 * @constructor Creates a `CourierResponse` with the specified properties.
 */
data class CourierResponse(
        val id: String,
        val lat: Double,
        val lng: Double,
        val storeName: String,
        val timestamp: LocalDateTime
)