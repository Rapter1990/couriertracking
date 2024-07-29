package com.casestudy.couriertracking.courier.model.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

/**
 * Data class representing a request to log a courier's location.
 *
 * @property courierId the UUID of the courier, which must be in a valid UUID format.
 * @property lat the latitude of the courier's location, which must be between -90 and 90.
 * @property lng the longitude of the courier's location, which must be between -180 and 180.
 * @property timestamp the timestamp of the location log, formatted as "dd/MM/yyyy HH:mm".
 * @constructor Creates a `LogCourierLocationRequest` with the specified properties.
 */
data class LogCourierLocationRequest(
        @field:NotBlank
        @field:Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid UUID format")
        val courierId: String,

        @field:DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
        @field:DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
        val lat: Double,

        @field:DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
        @field:DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
        val lng: Double,

        @field:JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        val timestamp: LocalDateTime
)