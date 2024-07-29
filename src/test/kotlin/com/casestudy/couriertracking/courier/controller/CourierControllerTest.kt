package com.casestudy.couriertracking.courier.controller

import com.casestudy.couriertracking.base.AbstractRestControllerTest
import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.dto.request.LogCourierLocationRequest
import com.casestudy.couriertracking.courier.model.dto.request.TravelQueryRequest
import com.casestudy.couriertracking.courier.model.mapper.CourierToCourierResponseMapper
import com.casestudy.couriertracking.courier.service.CourierService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CourierControllerTest : AbstractRestControllerTest() {

    @MockBean
    private lateinit var courierService: CourierService

    private final val courierToCourierResponseMapper: CourierToCourierResponseMapper = CourierToCourierResponseMapper.initialize()


    @Test
    fun `logCourierLocation should return success message`() {
        val logRequest = LogCourierLocationRequest(
                courierId = "123e4567-e89b-12d3-a456-426614174000",
                lat = 37.7749,
                lng = -122.4194,
                timestamp = LocalDateTime.now()
        )

        mockMvc.perform(MockMvcRequestBuilders.post("/api/couriers/log-location")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(logRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("Location logged successfully."))
    }

    @Test
    fun `getPastTravels should return a list of travels`() {
        val courierId = "123e4567-e89b-12d3-a456-426614174000"
        val travels = listOf(
                Courier(id = "1", courierId = courierId, lat = 37.7749, lng = -122.4194, storeName = "store1", timestamp = LocalDateTime.now()),
                Courier(id = "2", courierId = courierId, lat = 37.7750, lng = -122.4183, storeName = "store1", timestamp = LocalDateTime.now().minusHours(1))
        )


        val response = courierToCourierResponseMapper.map(travels)

        whenever(courierService.getPastTravelsByCourierId(courierId)).thenReturn(travels)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/couriers/travels/{courierId}", courierId))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].storeName").value(response.get(0).storeName))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[1].storeName").value(response.get(1).storeName))

    }

    @Test
    fun `getTravelsByCourierIdStoreNameAndTimeRange should return filtered travels`() {
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

        whenever(courierService.getTravelsByCourierIdStoreNameAndTimeRange(request)).thenReturn(travels)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/couriers/travels")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].storeName").value(response[0].storeName))

    }

    @Test
    fun `getTotalTravelDistance should return total distance traveled by a courier`() {
        val courierId = "123e4567-e89b-12d3-a456-426614174000"
        val totalDistance = 0.097 // Example distance in kilometers

        whenever(courierService.getTotalTravelDistance(courierId)).thenReturn(totalDistance)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/couriers/travels/{courierId}/total-distance", courierId))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value("0,10 km"))
    }

}