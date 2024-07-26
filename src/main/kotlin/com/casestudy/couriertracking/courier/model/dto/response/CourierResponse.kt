package com.casestudy.couriertracking.courier.model.dto.response

import java.time.LocalDateTime
import java.util.*

data class CourierResponse(
        val id: UUID,
        val lat: Double,
        val lng: Double,
        val timestamp: LocalDateTime
)