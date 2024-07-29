package com.casestudy.couriertracking.courier.repository

import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repository interface for managing `StoreEntity` entities.
 *
 * This interface extends `JpaRepository` to provide CRUD operations for `StoreEntity` entities.
 *
 * @see StoreEntity
 */
interface StoreRepository  : JpaRepository<StoreEntity, String>