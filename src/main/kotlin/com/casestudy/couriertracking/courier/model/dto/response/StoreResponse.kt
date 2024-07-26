package com.casestudy.couriertracking.courier.model.dto.response

import java.util.*

data class StoreResponse(
        val id: UUID,
        val name: String,
        val lat: Double,
        val lng: Double
)