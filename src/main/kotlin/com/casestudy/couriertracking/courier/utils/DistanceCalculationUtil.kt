package com.casestudy.couriertracking.courier.utils

import com.casestudy.couriertracking.courier.model.Location
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import com.casestudy.couriertracking.courier.strategy.DistanceInKilometersCalculatorStrategy
import com.casestudy.couriertracking.courier.strategy.DistanceInMetersCalculatorStrategy

/**
 * Utility object for calculating distances using different strategies.
 *
 * This object provides methods to calculate distances between two locations
 * using specified distance calculation strategies.
 */
object DistanceCalculationUtil {

    private val distanceCalculationStrategies = mapOf(
            DistanceType.METERS to DistanceInMetersCalculatorStrategy(),
            DistanceType.KILOMETERS to DistanceInKilometersCalculatorStrategy()
    )

    /**
     * Calculates the distance between two locations using the specified distance type.
     *
     * @param startLoc the starting location.
     * @param endLoc the ending location.
     * @param distanceType the type of distance measurement (meters or kilometers).
     * @return the distance between the two locations in the specified unit.
     * @throws IllegalArgumentException if the provided distance type is invalid.
     */
    fun calculateDistance(startLoc: Location, endLoc: Location, distanceType: DistanceType): Double {
        return distanceCalculationStrategies[distanceType]?.calculateDistance(startLoc, endLoc)
                ?: throw IllegalArgumentException("Invalid distance type")
    }

}