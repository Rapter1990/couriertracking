package com.casestudy.couriertracking.courier.service

import com.casestudy.couriertracking.base.AbstractBaseServiceTest
import com.casestudy.couriertracking.courier.exception.StoreFarAwayException
import com.casestudy.couriertracking.courier.exception.TimestampBeforeStoreCreateException
import com.casestudy.couriertracking.courier.model.dto.request.LogCourierLocationRequest
import com.casestudy.couriertracking.courier.model.dto.request.TravelQueryRequest
import com.casestudy.couriertracking.courier.model.entity.CourierEntity
import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import com.casestudy.couriertracking.courier.model.enums.DistanceType
import com.casestudy.couriertracking.courier.model.mapper.CourierEntityToCourierMapper
import com.casestudy.couriertracking.courier.repository.CourierRepository
import com.casestudy.couriertracking.courier.repository.StoreRepository
import com.casestudy.couriertracking.courier.utils.DistanceUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime
import java.util.*

/**
 * JUnit test class for CourierService.
 *
 * This class contains unit tests for the CourierService class,
 * ensuring the correct behavior of its methods.
 */
class CourierServiceTest : AbstractBaseServiceTest() {

    @InjectMocks
    private lateinit var courierService: CourierService

    @Mock
    private lateinit var courierRepository: CourierRepository

    @Mock
    private lateinit var storeRepository: StoreRepository

    private val courierEntityToCourierMapper: CourierEntityToCourierMapper = CourierEntityToCourierMapper.initialize()

    /**
     * Test for logCourierLocation method.
     *
     * This test ensures that the logCourierLocation method saves the courier's location
     * if it is within the radius of the store and the timestamp is valid.
     */
    @Test
    fun `logCourierLocation should save courier location if within radius and timestamp valid`() {

        // Given
        val courierId = "courier1"
        val lat = 37.7749
        val lng = -122.4194
        val timestamp = LocalDateTime.now()

        val logRequest = LogCourierLocationRequest(courierId, lat, lng, timestamp)
        val store = StoreEntity(
                name = "store1",
                lat = 37.7750,
                lng = -122.4183,
                createdAt = LocalDateTime.now().minusMinutes(10)
        )

        // Create a CourierEntity that should be saved
        val courierEntity = CourierEntity(
                id = UUID.randomUUID().toString(),
                courierId = courierId,
                lat = lat,
                lng = lng,
                storeName = store.name,
                timestamp = timestamp
        )

        // When
        whenever(storeRepository.findAll()).thenReturn(listOf(store))
        whenever(courierRepository.save(any<CourierEntity>())).thenReturn(courierEntity)

        // Then
        courierService.logCourierLocation(logRequest)

        // Verify
        verify(courierRepository).save(any())

    }

    /**
     * Test for logCourierLocation method.
     *
     * This test ensures that the logCourierLocation method throws a TimestampBeforeStoreCreateException
     * if the timestamp is before the store's creation time.
     */
    @Test
    fun `logCourierLocation should throw TimestampBeforeStoreCreateException if timestamp is before store creation`() {

        // Given
        val logRequest = LogCourierLocationRequest("courier1", 37.7749, -122.4194, LocalDateTime.now())
        val store = StoreEntity(
                name = "store1",
                lat = 37.7750,
                lng = -122.4183,
                createdAt = LocalDateTime.now().plusMinutes(10)
        )

        // When
        whenever(storeRepository.findAll()).thenReturn(listOf(store))

        // Throw
        assertThrows<TimestampBeforeStoreCreateException> {
            courierService.logCourierLocation(logRequest)
        }

    }

    /**
     * Test for logCourierLocation method.
     *
     * This test ensures that the logCourierLocation method throws a StoreFarAwayException
     * if the courier is far away from all stores.
     */
    @Test
    fun `logCourierLocation should throw StoreFarAwayException if courier is far away from all stores`() {

        // Given
        val logRequest = LogCourierLocationRequest("courier1", 37.7749, -122.4194, LocalDateTime.now())
        val store = StoreEntity(
                name = "store1",
                lat = 37.7800,
                lng = -122.4200,
                createdAt = LocalDateTime.now().minusMinutes(10)
        )

        // When
        whenever(storeRepository.findAll()).thenReturn(listOf(store))

        // Throw
        assertThrows<StoreFarAwayException> {
            courierService.logCourierLocation(logRequest)
        }

    }

    /**
     * Test for getPastTravelsByCourierId method.
     *
     * This test ensures that the getPastTravelsByCourierId method returns the travels
     * for a given courier ID.
     */
    @Test
    fun `getPastTravelsByCourierId should return travels for a given courier ID`() {

        // Given
        val courierId = "courier1"
        val courierEntities = listOf(
                CourierEntity(
                        id = UUID.randomUUID().toString(),
                        courierId = courierId,
                        lat = 37.7749,
                        lng = -122.4194,
                        storeName = "store1",
                        timestamp = LocalDateTime.now()
                )
        )

        val couriers = courierEntityToCourierMapper.map(courierEntities)

        // When
        whenever(courierRepository.findByCourierId(courierId)).thenReturn(courierEntities)

        // Then
        val result = courierService.getPastTravelsByCourierId(courierId)

        assertTrue(result.isNotEmpty())
        assertEquals(couriers, result)

        // Verify
        verify(courierRepository).findByCourierId(courierId)

    }

    /**
     * Test for getTravelsByCourierIdStoreNameAndTimeRange method.
     *
     * This test ensures that the getTravelsByCourierIdStoreNameAndTimeRange method returns the travels
     * within the specified time range for a given courier ID and store name.
     */
    @Test
    fun `getTravelsByCourierIdStoreNameAndTimeRange should return travels within time range`() {

        // Given
        val request = TravelQueryRequest("courier1", "store1", LocalDateTime.now().minusHours(1), LocalDateTime.now())
        val courierEntities = listOf(
                CourierEntity(
                        id = UUID.randomUUID().toString(),
                        courierId = "courier1",
                        lat = 37.7749,
                        lng = -122.4194,
                        storeName = "store1",
                        timestamp = LocalDateTime.now()
                )
        )

        val couriers = courierEntityToCourierMapper.map(courierEntities)

        // When
        whenever(courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(request.courierId, request.storeName, request.start, request.end)).thenReturn(courierEntities)

        // Then
        val result = courierService.getTravelsByCourierIdStoreNameAndTimeRange(request)

        assertTrue(result.isNotEmpty())
        assertEquals(couriers, result)

        // Verify
        verify(courierRepository).findByCourierIdAndStoreNameAndTimestampBetween(request.courierId, request.storeName, request.start, request.end)

    }

    /**
     * Test for getTotalTravelDistance method.
     *
     * This test ensures that the getTotalTravelDistance method returns the total distance
     * traveled by a courier.
     */
    @Test
    fun `getTotalTravelDistance should return total distance traveled by a courier`() {

        // Given
        val courierId = "courier1"
        val timestamp1 = LocalDateTime.now().minusMinutes(2)
        val timestamp2 = LocalDateTime.now()

        // Coordinates for two locations
        val lat1 = 37.7749
        val lng1 = -122.4194
        val lat2 = 37.7750
        val lng2 = -122.4183

        val travels = listOf(
                CourierEntity(
                        id = UUID.randomUUID().toString(),
                        courierId = courierId,
                        lat = lat1,
                        lng = lng1,
                        storeName = "store1",
                        timestamp = timestamp1
                ),
                CourierEntity(
                        id = UUID.randomUUID().toString(),
                        courierId = courierId,
                        lat = lat2,
                        lng = lng2,
                        storeName = "store1",
                        timestamp = timestamp2
                )
        )

        // Calculate the distance between two points in kilometers
        val distanceInKilometers = DistanceUtils.calculateDistance(lat1, lng1, lat2, lng2, DistanceType.KILOMETERS)

        // When
        whenever(courierRepository.findByCourierIdOrderByTimestampAsc(courierId)).thenReturn(travels)

        // Then
        val totalDistance = courierService.getTotalTravelDistance(courierId)

        assertEquals(distanceInKilometers, totalDistance, 0.001) // Allow a small tolerance for floating point comparisons

        // Verify
        verify(courierRepository).findByCourierIdOrderByTimestampAsc(courierId)

    }

}