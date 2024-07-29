package com.casestudy.couriertracking.courier.utils

import com.casestudy.couriertracking.courier.model.Location
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import java.time.Duration
import java.time.LocalDateTime

object DistanceUtils {

    fun isWithinRadius(courierLat: Double, courierLng: Double, storeLat: Double, storeLng: Double, radiusInMeters: Double): Boolean {
        val radiusInKm = radiusInMeters / 1000.0
        val courierLocation = Location(courierLat, courierLng)
        val storeLocation = Location(storeLat, storeLng)
        val distance = DistanceCalculationUtil.calculateDistance(courierLocation, storeLocation, DistanceType.KILOMETERS)
        return distance <= radiusInKm
    }

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double, distanceType: DistanceType): Double {
        val startLocation = Location(lat1, lng1)
        val endLocation = Location(lat2, lng2)
        return DistanceCalculationUtil.calculateDistance(startLocation, endLocation, distanceType)
    }

    fun isMoreThanOneMinuteAgo(lastTimestamp: LocalDateTime, currentTimestamp: LocalDateTime): Boolean {
        return Duration.between(lastTimestamp, currentTimestamp).toMinutes() > 1
    }
}