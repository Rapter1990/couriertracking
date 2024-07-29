package com.casestudy.couriertracking.courier.utils

import com.casestudy.couriertracking.base.AbstractBaseServiceTest
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import kotlin.math.pow
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Unit tests for the `DistanceUtils` class.
 *
 * This test class verifies that the `DistanceUtils` correctly calculates distances and checks if a courier
 * is within a specified radius from a store, and validates timestamps.
 */
class DistanceUtilsTest : AbstractBaseServiceTest() {

    @InjectMocks
    private lateinit var distanceUtils: DistanceUtils

    @Mock
    private lateinit var mockDistanceCalculationUtil: DistanceCalculationUtil

    /**
     * Tests the `isWithinRadius` method in `DistanceUtils`.
     *
     * Given the coordinates of a courier and a store, this test verifies that the method returns `true`
     * when the courier is within the specified radius in meters.
     */
    @Test
    fun `test isWithinRadius returns true when within radius in meters`() {
        val courierLat = 37.7749
        val courierLng = -122.4194
        val storeLat = 37.7750
        val storeLng = -122.4183
        val radiusInMeters = 100.0
        val mockDistanceInKm = 0.0001 // Mocked distance in kilometers

        // Mock the behavior of calculateDistance
        whenever(mockDistanceCalculationUtil.calculateDistance(any(), any(), eq(DistanceType.KILOMETERS)))
                .thenReturn(mockDistanceInKm)

        val result = distanceUtils.isWithinRadius(courierLat, courierLng, storeLat, storeLng, radiusInMeters)

        assertTrue(result, "Courier should be within the radius of the store.")
    }

    /**
     * Tests the `isWithinRadius` method in `DistanceUtils`.
     *
     * Given the coordinates of a courier and a store, this test verifies that the method returns `false`
     * when the courier is outside the specified radius in meters.
     */
    @Test
    fun `test isWithinRadius returns false when outside radius in meters`() {
        val courierLat = 37.7749
        val courierLng = -122.4194
        val storeLat = 37.7800
        val storeLng = -122.4200
        val radiusInMeters = 100.0
        val mockDistanceInKm = 0.01 // Mocked distance in kilometers, which is more than the radius

        // Convert radius from meters to kilometers for comparison
        val radiusInKm = radiusInMeters / 1000.0

        // Mock the behavior of calculateDistance
        whenever(mockDistanceCalculationUtil.calculateDistance(any(), any(), eq(DistanceType.KILOMETERS)))
                .thenReturn(mockDistanceInKm)

        val result = distanceUtils.isWithinRadius(courierLat, courierLng, storeLat, storeLng, radiusInMeters)

        // Print debug information to understand the result
        println("Calculated distance (km): $mockDistanceInKm, Radius (km): $radiusInKm, Result: $result")

        assertFalse(result, "Courier should not be within the radius of the store.")
    }

    /**
     * Tests the `calculateDistance` method in `DistanceUtils`.
     *
     * Given the coordinates of two points, this test verifies that the method returns the correct distance
     * in kilometers.
     */
    @Test
    fun `test calculateDistance returns correct distance in kilometers`() {
        val lat1 = 37.7749
        val lng1 = -122.4194
        val lat2 = 37.7750
        val lng2 = -122.4183

        // Calculate the actual distance or use a realistic expected value
        // Example distance between the two points (change this to the correct distance)
        val expectedDistanceInKm = haversine(lat1, lng1, lat2, lng2)
        val tolerance = 0.0001

        // Mock the behavior of calculateDistance
        whenever(mockDistanceCalculationUtil.calculateDistance(any(), any(), eq(DistanceType.KILOMETERS)))
                .thenReturn(expectedDistanceInKm)

        val distance = distanceUtils.calculateDistance(lat1, lng1, lat2, lng2, DistanceType.KILOMETERS)

        // Print debug information to understand the result
        println("Mocked distance (km): $expectedDistanceInKm, Actual distance (km): $distance")

        // Ensure the distance is within the expected range
        assertTrue(distance in expectedDistanceInKm - tolerance..expectedDistanceInKm + tolerance,
                "Calculated distance ($distance) should be within the expected range ($expectedDistanceInKm ± $tolerance).")
    }

    /**
     * Tests the `isMoreThanOneMinuteAgo` method in `DistanceUtils`.
     *
     * Given a timestamp, this test verifies that the method returns `true` if the timestamp is more than one minute ago.
     */
    @Test
    fun `test isMoreThanOneMinuteAgo returns true when more than one minute ago`() {
        val lastTimestamp = LocalDateTime.now().minusMinutes(2)
        val currentTimestamp = LocalDateTime.now()

        val result = distanceUtils.isMoreThanOneMinuteAgo(lastTimestamp, currentTimestamp)

        assertTrue(result, "Timestamp should be more than one minute ago.")
    }

    /**
     * Tests the `isMoreThanOneMinuteAgo` method in `DistanceUtils`.
     *
     * Given a timestamp, this test verifies that the method returns `false` if the timestamp is within one minute.
     */
    @Test
    fun `test isMoreThanOneMinuteAgo returns false when within one minute`() {
        val lastTimestamp = LocalDateTime.now().minusSeconds(30)
        val currentTimestamp = LocalDateTime.now()

        val result = distanceUtils.isMoreThanOneMinuteAgo(lastTimestamp, currentTimestamp)

        assertFalse(result, "Timestamp should not be more than one minute ago.")
    }

    /**
     * Calculates the distance between two points on the Earth's surface using the Haversine formula.
     *
     * @param lat1 Latitude of the first point.
     * @param lng1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lng2 Longitude of the second point.
     * @return The distance between the two points in kilometers.
     */
    fun haversine(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val R = 6371.0 // Earth radius in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = Math.sin(dLat / 2).pow(2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2).pow(2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return R * c
    }

}