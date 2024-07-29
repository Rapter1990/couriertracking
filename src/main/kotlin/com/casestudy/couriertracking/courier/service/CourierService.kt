package com.casestudy.couriertracking.courier.service

import com.casestudy.couriertracking.courier.exception.CourierNotFoundException
import com.casestudy.couriertracking.courier.exception.StoreFarAwayException
import com.casestudy.couriertracking.courier.exception.StoreNotFoundException
import com.casestudy.couriertracking.courier.exception.TimestampBeforeStoreCreateException
import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.dto.request.LogCourierLocationRequest
import com.casestudy.couriertracking.courier.model.dto.request.TravelQueryRequest
import com.casestudy.couriertracking.courier.model.entity.CourierEntity
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import com.casestudy.couriertracking.courier.model.mapper.CourierEntityToCourierMapper
import com.casestudy.couriertracking.courier.repository.CourierRepository
import com.casestudy.couriertracking.courier.repository.StoreRepository
import com.casestudy.couriertracking.courier.utils.DistanceUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CourierService(
        private val courierRepository: CourierRepository,
        private val storeRepository: StoreRepository
) {

    private final val courierEntityToCourierMapper: CourierEntityToCourierMapper = CourierEntityToCourierMapper.initialize()

    fun logCourierLocation(logRequest: LogCourierLocationRequest) {
        val (courierId, lat, lng, timestamp) = logRequest
        val stores = storeRepository.findAll()

        Optional.ofNullable(stores)
                .orElseThrow { StoreNotFoundException("No stores found in the database.") }

        var travelEntrySaved = false

        for (store in stores) {
            if (DistanceUtils.isWithinRadius(lat, lng, store.lat, store.lng, 100.0) ) {

                if (timestamp.isBefore(store.createdAt)) {
                    throw TimestampBeforeStoreCreateException("Timestamp is before store's creation time.")
                }

                val lastTravel = findLastTravelEntry(courierId, store.name, timestamp)

                if (lastTravel == null || DistanceUtils.isMoreThanOneMinuteAgo(lastTravel.timestamp, timestamp)) {
                    val courier = CourierEntity(
                            courierId = courierId,
                            lat = lat,
                            lng = lng,
                            storeName = store.name,
                            timestamp = timestamp
                    )

                    courierRepository.save(courier)
                    travelEntrySaved = true
                }
            }
        }

        if (!travelEntrySaved) {
            throw StoreFarAwayException("Courier is far away from all stores.")
        }
    }

    private fun findLastTravelEntry(courierId: String, storeName: String, currentTimestamp: LocalDateTime): CourierEntity? {
        val oneMinuteAgo = currentTimestamp.minusMinutes(1)
        return courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(courierId, storeName, oneMinuteAgo, currentTimestamp)
                .maxByOrNull { it.timestamp }
    }

    fun getPastTravelsByCourierId(courierId: String): List<Courier> {
        val entities = courierRepository.findByCourierId(courierId)
        Optional.ofNullable(entities)
                .filter { it.isNotEmpty() }
                .orElseThrow { CourierNotFoundException("Courier with ID $courierId not found.") }
        return courierEntityToCourierMapper.map(entities)
    }

    fun getTravelsByCourierIdStoreNameAndTimeRange(request: TravelQueryRequest): List<Courier> {
        val (courierId, storeName, start, end) = request
        val entities = courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(courierId, storeName, start, end)
        Optional.ofNullable(entities)
                .filter { it.isNotEmpty() }
                .orElseThrow { CourierNotFoundException("No travels found for Courier ID $courierId in store $storeName between $start and $end.") }
        return courierEntityToCourierMapper.map(entities)
    }

    fun getTotalTravelDistance(courierId: String): Double {
        val travels = courierRepository.findByCourierIdOrderByTimestampAsc(courierId)
        Optional.ofNullable(travels)
                .filter { it.isNotEmpty() }
                .orElseThrow { CourierNotFoundException("No travel records found for Courier ID $courierId.") }

        var totalDistance = 0.0

        for (i in 1 until travels.size) {
            val prevTravel = travels[i - 1]
            val currentTravel = travels[i]
            totalDistance += DistanceUtils.calculateDistance(prevTravel.lat, prevTravel.lng, currentTravel.lat, currentTravel.lng, DistanceType.KILOMETERS)
        }

        return totalDistance
    }
}