package com.casestudy.couriertracking.courier.model

import java.time.LocalDateTime

data class Courier(
        val id: String,
        val courierId: String,
        val lat: Double,
        val lng: Double,
        val storeName: String,
        val timestamp: LocalDateTime
)