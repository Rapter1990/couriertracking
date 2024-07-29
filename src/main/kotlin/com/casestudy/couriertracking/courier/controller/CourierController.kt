package com.casestudy.couriertracking.courier.controller

import com.casestudy.couriertracking.common.model.dto.response.CustomResponse
import com.casestudy.couriertracking.courier.model.dto.request.LogCourierLocationRequest
import com.casestudy.couriertracking.courier.model.dto.request.TravelQueryRequest
import com.casestudy.couriertracking.courier.model.dto.response.CourierResponse
import com.casestudy.couriertracking.courier.model.mapper.CourierToCourierResponseMapper
import com.casestudy.couriertracking.courier.service.CourierService
import jakarta.validation.Valid
import org.hibernate.validator.constraints.UUID
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/couriers")
@Validated
class CourierController(private val courierService: CourierService) {

    private final val courierToCourierResponseMapper: CourierToCourierResponseMapper = CourierToCourierResponseMapper.initialize()

    /**
     * Log a courier's location.
     */
    @PostMapping("/log-location")
    fun logCourierLocation(
            @RequestBody @Valid logRequest: LogCourierLocationRequest
    ): CustomResponse<String> {
        courierService.logCourierLocation(logRequest)
        return CustomResponse.successOf("Location logged successfully.")
    }

    /**
     * Get past travels of a courier by courier ID.
     */
    @GetMapping("/travels/{courierId}")
    fun getPastTravels(
            @PathVariable @UUID courierId: String
    ): CustomResponse<List<CourierResponse>> {
        val travels = courierService.getPastTravelsByCourierId(courierId)
        val response = courierToCourierResponseMapper.map(travels)
        return CustomResponse.successOf(response)
    }

    /**
     * Get travels of a courier by courier ID, store name, and time range.
     */
    @GetMapping("/travels")
    fun getTravelsByCourierIdStoreNameAndTimeRange(
            @RequestBody request: TravelQueryRequest
    ): CustomResponse<List<CourierResponse>> {
        val travels = courierService.getTravelsByCourierIdStoreNameAndTimeRange(request)
        val response = courierToCourierResponseMapper.map(travels)
        return CustomResponse.successOf(response)
    }

    /**
     * Get total travel distance of a courier by courier ID.
     */
    @GetMapping("/travels/{courierId}/total-distance")
    fun getTotalTravelDistance(
            @PathVariable @UUID courierId: String
    ): CustomResponse<String> {
        val totalDistanceInKm = courierService.getTotalTravelDistance(courierId)
        val formattedDistance = String.format("%.2f km", totalDistanceInKm)
        return CustomResponse.successOf(formattedDistance)
    }

}
