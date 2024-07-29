package com.casestudy.couriertracking.courier.repository

import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository


interface StoreRepository  : JpaRepository<StoreEntity, String>