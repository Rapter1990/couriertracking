package com.casestudy.couriertracking.courier.strategy

import com.casestudy.couriertracking.courier.model.Location

/**
 * Interface for distance calculation strategies between two locations.
 *
 * Implementations of this interface define different methods for calculating distances
 * between two geographical points.
 */
interface DistanceCalculationStrategy {

    /**
     * Calculates the distance between two locations.
     *
     * @param startLoc the starting location.
     * @param endLoc the ending location.
     * @return the distance between the two locations.
     */
    fun calculateDistance(startLoc: Location, endLoc: Location): Double

}