package com.casestudy.couriertracking.courier.model.dto.response

import java.time.LocalDateTime

data class CourierResponse(
        val id: String,
        val lat: Double,
        val lng: Double,
        val storeName: String,
        val timestamp: LocalDateTime
)