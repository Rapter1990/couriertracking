package com.casestudy.couriertracking.courier.repository

import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface  StoreRepository  : JpaRepository<StoreEntity, UUID>