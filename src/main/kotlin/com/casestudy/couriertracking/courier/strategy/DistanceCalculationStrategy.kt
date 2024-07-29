package com.casestudy.couriertracking.courier.strategy

import com.casestudy.couriertracking.courier.model.Location

interface DistanceCalculationStrategy {
    fun calculateDistance(startLoc: Location, endLoc: Location): Double
}