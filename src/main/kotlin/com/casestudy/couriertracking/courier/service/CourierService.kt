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

/**
 * Service class for managing courier-related operations.
 *
 * This service handles logging courier locations, retrieving past travels,
 * querying travels within a time range, and calculating total travel distance.
 *
 * @property courierRepository the repository for managing `CourierEntity` entities.
 * @property storeRepository the repository for managing `StoreEntity` entities.
 * @property courierEntityToCourierMapper the mapper for converting `CourierEntity` to `Courier` data class.
 */
@Service
class CourierService(
        private val courierRepository: CourierRepository,
        private val storeRepository: StoreRepository
) {

    private final val courierEntityToCourierMapper: CourierEntityToCourierMapper = CourierEntityToCourierMapper.initialize()

    /**
     * Logs the location of a courier.
     *
     * This method processes a `LogCourierLocationRequest` to save the courier's location if it is within
     * the radius of any store and the timestamp is after the store's creation time.
     * If no valid travel entry is saved, a `StoreFarAwayException` is thrown.
     *
     * @param logRequest the request containing the courier's location and timestamp.
     * @throws StoreNotFoundException if no stores are found in the database.
     * @throws TimestampBeforeStoreCreateException if the timestamp is before the store's creation time.
     * @throws StoreFarAwayException if the courier is far away from all stores.
     */
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

    /**
     * Finds the last travel entry of a courier to a store within a given timestamp range.
     *
     * @param courierId the ID of the courier.
     * @param storeName the name of the store.
     * @param currentTimestamp the current timestamp to check against.
     * @return the last travel entry of the courier to the store within the timestamp range, or null if none found.
     */
    private fun findLastTravelEntry(courierId: String, storeName: String, currentTimestamp: LocalDateTime): CourierEntity? {
        val oneMinuteAgo = currentTimestamp.minusMinutes(1)
        return courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(courierId, storeName, oneMinuteAgo, currentTimestamp)
                .maxByOrNull { it.timestamp }
    }

    /**
     * Retrieves past travels of a courier by courier ID.
     *
     * @param courierId the ID of the courier.
     * @return a list of `Courier` data classes representing past travels of the courier.
     * @throws CourierNotFoundException if no travels are found for the given courier ID.
     */
    fun getPastTravelsByCourierId(courierId: String): List<Courier> {
        val entities = courierRepository.findByCourierId(courierId)
        Optional.ofNullable(entities)
                .filter { it.isNotEmpty() }
                .orElseThrow { CourierNotFoundException("Courier with ID $courierId not found.") }
        return courierEntityToCourierMapper.map(entities)
    }

    /**
     * Retrieves travels of a courier by courier ID, store name, and time range.
     *
     * @param request the request containing courier ID, store name, start time, and end time.
     * @return a list of `Courier` data classes representing travels within the specified time range.
     * @throws CourierNotFoundException if no travels are found for the given criteria.
     */
    fun getTravelsByCourierIdStoreNameAndTimeRange(request: TravelQueryRequest): List<Courier> {
        val (courierId, storeName, start, end) = request
        val entities = courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(courierId, storeName, start, end)
        Optional.ofNullable(entities)
                .filter { it.isNotEmpty() }
                .orElseThrow { CourierNotFoundException("No travels found for Courier ID $courierId in store $storeName between $start and $end.") }
        return courierEntityToCourierMapper.map(entities)
    }

    /**
     * Calculates the total travel distance of a courier.
     *
     * @param courierId the ID of the courier.
     * @return the total travel distance in kilometers.
     * @throws CourierNotFoundException if no travel records are found for the given courier ID.
     */
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