package com.casestudy.couriertracking.courier.repository

import com.casestudy.couriertracking.courier.model.entity.CourierEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

/**
 * Repository interface for managing `CourierEntity` entities.
 *
 * This interface extends `JpaRepository` to provide CRUD operations and custom query methods
 * for `CourierEntity` entities.
 *
 * @see CourierEntity
 */
interface CourierRepository : JpaRepository<CourierEntity, String> {

    /**
     * Finds a list of `CourierEntity` by the given `courierId`.
     *
     * @param courierId the ID of the courier to search for.
     * @return a list of `CourierEntity` that match the given `courierId`.
     */
    fun findByCourierId(courierId: String): List<CourierEntity>

    /**
     * Finds a list of `CourierEntity` by the given `courierId`, `storeName`, and a timestamp range.
     *
     * @param courierId the ID of the courier to search for.
     * @param storeName the name of the store to filter by.
     * @param start the start of the timestamp range.
     * @param end the end of the timestamp range.
     * @return a list of `CourierEntity` that match the given criteria.
     */
    fun findByCourierIdAndStoreNameAndTimestampBetween(courierId: String, storeName: String, start: LocalDateTime, end: LocalDateTime): List<CourierEntity>

    /**
     * Finds a list of `CourierEntity` by the given `courierId` and orders the results by timestamp in ascending order.
     *
     * @param courierId the ID of the courier to search for.
     * @return a list of `CourierEntity` ordered by timestamp in ascending order.
     */
    fun findByCourierIdOrderByTimestampAsc(courierId: String): List<CourierEntity>

}