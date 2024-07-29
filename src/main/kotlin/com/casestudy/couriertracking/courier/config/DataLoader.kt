package com.casestudy.couriertracking.courier.config

import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import com.casestudy.couriertracking.courier.repository.StoreRepository
import lombok.RequiredArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@RequiredArgsConstructor
class DataLoader(private val storeRepository: StoreRepository) {

    @Bean
    fun loadInitialData(): CommandLineRunner {
        return CommandLineRunner {
            val stores = listOf(
                    StoreEntity(
                            name = "Market 1",
                            lat = 40.9923307,
                            lng = 29.1244229
                    ),
                    StoreEntity(
                            name = "Market 2",
                            lat = 40.986106,
                            lng = 29.1161293
                    ),
                    StoreEntity(
                            name = "Market 3",
                            lat = 41.0066851,
                            lng = 28.6552262
                    ),
                    StoreEntity(
                            name = "Market 4",
                            lat = 41.055783,
                            lng = 29.0210292
                    ),
                    StoreEntity(
                            name = "Market 5",
                            lat = 40.9632463,
                            lng = 29.0630908
                    )
            )
            storeRepository.saveAll(stores)
        }
    }
}