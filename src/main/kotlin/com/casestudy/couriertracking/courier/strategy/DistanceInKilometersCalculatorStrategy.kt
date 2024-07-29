package com.casestudy.couriertracking.courier.strategy

import com.casestudy.couriertracking.courier.model.Location
import kotlin.math.*

class DistanceInKilometersCalculatorStrategy : DistanceCalculationStrategy {
    private val earthRadiusInKilometers = 6371.0

    override fun calculateDistance(startLoc: Location, endLoc: Location): Double {
        val centralAngle = calculateGreatCircleDistance(startLoc, endLoc)
        return earthRadiusInKilometers * centralAngle
    }

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