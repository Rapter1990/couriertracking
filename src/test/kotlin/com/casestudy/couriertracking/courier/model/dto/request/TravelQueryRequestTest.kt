package com.casestudy.couriertracking.courier.model.dto.request

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class TravelQueryRequestTest {

    @Test
    fun `should throw IllegalArgumentException when start time is after end time`() {
        // Given
        val start = LocalDateTime.now().plusDays(1) // Future date
        val end = LocalDateTime.now() // Current date

        // When & Then
        assertThrows<IllegalArgumentException> {
            TravelQueryRequest(
                    courierId = "123e4567-e89b-12d3-a456-426614174000",
                    storeName = "Store",
                    start = start,
                    end = end
            )
        }.apply {
            assertEquals("Start time must be before end time", message)
        }
    }
}