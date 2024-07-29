package com.casestudy.couriertracking.courier.utils

import com.casestudy.couriertracking.courier.model.Location
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import java.time.Duration
import java.time.LocalDateTime

/**
 * Utility object for various distance-related operations.
 *
 * This object provides methods to check if a location is within a certain radius
 * of another location, calculate distances, and determine if a given timestamp
 * is more than one minute ago.
 */
object DistanceUtils {

    /**
     * Checks if a courier's location is within a specified radius of a store's location.
     *
     * @param courierLat the latitude of the courier's location.
     * @param courierLng the longitude of the courier's location.
     * @param storeLat the latitude of the store's location.
     * @param storeLng the longitude of the store's location.
     * @param radiusInMeters the radius in meters to check within.
     * @return `true` if the courier is within the specified radius of the store, `false` otherwise.
     */
    fun isWithinRadius(courierLat: Double, courierLng: Double, storeLat: Double, storeLng: Double, radiusInMeters: Double): Boolean {
        val radiusInKm = radiusInMeters / 1000.0
        val courierLocation = Location(courierLat, courierLng)
        val storeLocation = Location(storeLat, storeLng)
        val distance = DistanceCalculationUtil.calculateDistance(courierLocation, storeLocation, DistanceType.KILOMETERS)
        return distance <= radiusInKm
    }

    /**
     * Calculates the distance between two geographical points using the specified distance type.
     *
     * @param lat1 the latitude of the first location.
     * @param lng1 the longitude of the first location.
     * @param lat2 the latitude of the second location.
     * @param lng2 the longitude of the second location.
     * @param distanceType the type of distance measurement (meters or kilometers).
     * @return the distance between the two locations in the specified unit.
     */
    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double, distanceType: DistanceType): Double {
        val startLocation = Location(lat1, lng1)
        val endLocation = Location(lat2, lng2)
        return DistanceCalculationUtil.calculateDistance(startLocation, endLocation, distanceType)
    }

    /**
     * Checks if a given timestamp is more than one minute ago compared to the current timestamp.
     *
     * @param lastTimestamp the last timestamp to compare.
     * @param currentTimestamp the current timestamp to compare against.
     * @return `true` if the `lastTimestamp` is more than one minute before the `currentTimestamp`, `false` otherwise.
     */
    fun isMoreThanOneMinuteAgo(lastTimestamp: LocalDateTime, currentTimestamp: LocalDateTime): Boolean {
        return Duration.between(lastTimestamp, currentTimestamp).toMinutes() > 1
    }

}