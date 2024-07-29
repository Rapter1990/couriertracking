package com.casestudy.couriertracking.courier.model.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "stores")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
data class StoreEntity(

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", updatable = false, nullable = false)
        val id: String = UUID.randomUUID().toString(),

        val name: String,

        val lat: Double,

        val lng: Double,

        @Column(name = "created_at")
        val createdAt: LocalDateTime = LocalDateTime.now()

)