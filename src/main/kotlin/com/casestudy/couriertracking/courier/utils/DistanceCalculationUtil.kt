package com.casestudy.couriertracking.courier.utils

import com.casestudy.couriertracking.courier.model.Location
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import com.casestudy.couriertracking.courier.strategy.DistanceInKilometersCalculatorStrategy
import com.casestudy.couriertracking.courier.strategy.DistanceInMetersCalculatorStrategy

object DistanceCalculationUtil {
    private val distanceCalculationStrategies = mapOf(
            DistanceType.METERS to DistanceInMetersCalculatorStrategy(),
            DistanceType.KILOMETERS to DistanceInKilometersCalculatorStrategy()
    )

    fun calculateDistance(startLoc: Location, endLoc: Location, distanceType: DistanceType): Double {
        return distanceCalculationStrategies[distanceType]?.calculateDistance(startLoc, endLoc)
                ?: throw IllegalArgumentException("Invalid distance type")
    }
}