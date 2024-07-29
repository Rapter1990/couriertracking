package com.casestudy.couriertracking.courier.repository

import com.casestudy.couriertracking.courier.model.entity.CourierEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface CourierRepository : JpaRepository<CourierEntity, String> {

    fun findByCourierId(courierId: String): List<CourierEntity>

    fun findByCourierIdAndStoreNameAndTimestampBetween(courierId: String, storeName: String, start: LocalDateTime, end: LocalDateTime): List<CourierEntity>

    fun findByCourierIdOrderByTimestampAsc(courierId: String): List<CourierEntity>
}