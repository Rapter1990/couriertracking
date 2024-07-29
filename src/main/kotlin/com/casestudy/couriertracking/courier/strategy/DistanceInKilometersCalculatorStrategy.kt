package com.casestudy.couriertracking.courier.strategy

import com.casestudy.couriertracking.courier.model.Location
import kotlin.math.*

/**
 * Strategy for calculating distance between two locations in kilometers.
 *
 * This strategy uses the Haversine formula to calculate the great-circle distance
 * between two points on the Earth's surface and returns the result in kilometers.
 */
class DistanceInKilometersCalculatorStrategy : DistanceCalculationStrategy {
    private val earthRadiusInKilometers = 6371.0

    /**
     * Calculates the distance between two locations in kilometers.
     *
     * @param startLoc the starting location.
     * @param endLoc the ending location.
     * @return the distance between the two locations in kilometers.
     */
    override fun calculateDistance(startLoc: Location, endLoc: Location): Double {
        val centralAngle = calculateGreatCircleDistance(startLoc, endLoc)
        return earthRadiusInKilometers * centralAngle
    }

    /**
     * Calculates the great-circle distance between two points using the Haversine formula.
     *
     * @param startLoc the starting location.
     * @param endLoc the ending location.
     * @return the central angle between the two locations.
     */
    private fun calculateGreatCircleDistance(startLoc: Location, endLoc: Location): Double {
        val latOne = Math.toRadians(startLoc.latitude)
        val lngOne = Math.toRadians(startLoc.longitude)
        val latTwo = Math.toRadians(endLoc.latitude)
        val lngTwo = Math.toRadians(endLoc.longitude)

        val diffOfLat = latTwo - latOne
        val diffOfLng = lngTwo - lngOne

        val ax = sin(diffOfLat / 2).pow(2) + cos(latOne) * cos(latTwo) * sin(diffOfLng / 2).pow(2)
        return 2 * atan2(sqrt(ax), sqrt(1 - ax))
    }
}