package com.casestudy.couriertracking.courier.model.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.*
import java.time.LocalDateTime

data class TravelQueryRequest(
        @field:NotBlank
        @field:Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Invalid UUID format")
        val courierId: String,

        @field:NotBlank(message = "Store name cannot be blank")
        val storeName: String,

        @field:NotNull(message = "Start time cannot be null")
        @field:PastOrPresent(message = "Start time must be in the past or present")
        @field:JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        val start: LocalDateTime,

        @field:NotNull(message = "End time cannot be null")
        @field:FutureOrPresent(message = "End time must be in the future or present")
        @field:JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        val end: LocalDateTime
) {
        init {
                if (start.isAfter(end)) {
                        throw IllegalArgumentException("Start time must be before end time")
                }
        }
}