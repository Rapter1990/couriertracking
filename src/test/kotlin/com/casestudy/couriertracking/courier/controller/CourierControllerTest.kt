package com.casestudy.couriertracking.courier.controller

import com.casestudy.couriertracking.base.AbstractRestControllerTest
import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.dto.request.LogCourierLocationRequest
import com.casestudy.couriertracking.courier.model.dto.request.TravelQueryRequest
import com.casestudy.couriertracking.courier.model.mapper.CourierToCourierResponseMapper
import com.casestudy.couriertracking.courier.service.CourierService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Integration tests for CourierController.
 * This class tests the functionality of CourierController's endpoints.
 */
class CourierControllerTest : AbstractRestControllerTest() {

    @MockBean
    private lateinit var courierService: CourierService

    private final val courierToCourierResponseMapper: CourierToCourierResponseMapper = CourierToCourierResponseMapper.initialize()

    /**
     * Test that the logCourierLocation endpoint returns a success message.
     */
    @Test
    fun `logCourierLocation should return success message`() {

        // Given
        val logRequest = LogCourierLocationRequest(
                courierId = "123e4567-e89b-12d3-a456-426614174000",
                lat = 37.7749,
                lng = -122.4194,
                timestamp = LocalDateTime.now()
        )

        // When
        doNothing().`when`(courierService).logCourierLocation(any())


        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/couriers/log-location")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(logRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("Location logged successfully."))

        // Verify
        verify(courierService).logCourierLocation(any())

    }

    /**
     * Test that the getPastTravels endpoint returns a list of travels for a given courier ID.
     */
    @Test
    fun `getPastTravels should return a list of travels`() {

        // Given
        val courierId = "123e4567-e89b-12d3-a456-426614174000"
        val travels = listOf(
                Courier(id = "1", courierId = courierId, lat = 37.7749, lng = -122.4194, storeName = "store1", timestamp = LocalDateTime.now()),
                Courier(id = "2", courierId = courierId, lat = 37.7750, lng = -122.4183, storeName = "store1", timestamp = LocalDateTime.now().minusHours(1))
        )


        val response = courierToCourierResponseMapper.map(travels)

        // When
        whenever(courierService.getPastTravelsByCourierId(courierId)).thenReturn(travels)

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/couriers/travels/{courierId}", courierId))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].storeName").value(response.get(0).storeName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[1].storeName").value(response.get(1).storeName))

        // Verify
        verify(courierService).getPastTravelsByCourierId(courierId)

    }

    /**
     * Test that the getTravelsByCourierIdStoreNameAndTimeRange endpoint returns filtered travels.
     */
    @Test
    fun `getTravelsByCourierIdStoreNameAndTimeRange should return filtered travels`() {

        // Given
        val request = TravelQueryRequest(
                courierId = "123e4567-e89b-12d3-a456-426614174000",
                storeName = "Ataşehir MMM Migros",
                start = LocalDateTime.parse("27/07/2024 01:58", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                end = LocalDateTime.parse("27/07/2024 02:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        )

        val travels = listOf(
                Courier(
                        id = "1",
                        courierId = request.courierId,
                        lat = 37.7749,
                        lng = -122.4194,
                        storeName = request.storeName,
                        timestamp = LocalDateTime.parse("27/07/2024 01:59", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                )
        )

        val response = courierToCourierResponseMapper.map(travels)

        // When
        whenever(courierService.getTravelsByCourierIdStoreNameAndTimeRange(request)).thenReturn(travels)

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/couriers/travels")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].storeName").value(response[0].storeName))

        // Verify
        verify(courierService).getTravelsByCourierIdStoreNameAndTimeRange(request)

    }

    /**
     * Test that the getTotalTravelDistance endpoint returns the total distance traveled by a courier.
     */
    @Test
    fun `getTotalTravelDistance should return total distance traveled by a courier`() {

        // Given
        val courierId = "123e4567-e89b-12d3-a456-426614174000"
        val totalDistance = 0.097 // Example distance in kilometers
        val formattedDistance = String.format("%.2f km", totalDistance)

        // When
        whenever(courierService.getTotalTravelDistance(courierId)).thenReturn(totalDistance)

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/couriers/travels/{courierId}/total-distance", courierId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(formattedDistance))

        // Verify
        verify(courierService).getTotalTravelDistance(courierId)

    }

}