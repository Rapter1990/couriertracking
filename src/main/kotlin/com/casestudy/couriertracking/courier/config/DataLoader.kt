package com.casestudy.couriertracking.courier.config

import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import com.casestudy.couriertracking.courier.repository.StoreRepository
import lombok.RequiredArgsConstructor
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for loading initial data into the database.
 *
 * This class uses Spring Boot's `@Configuration` annotation to define
 * a configuration for loading initial data. It uses the `@RequiredArgsConstructor`
 * annotation for dependency injection.
 *
 * @property storeRepository the repository used to interact with the store data.
 * @constructor Creates a DataLoader instance with the given `storeRepository`.
 */
@Configuration
@RequiredArgsConstructor
class DataLoader(private val storeRepository: StoreRepository) {

    /**
     * Loads initial data into the database.
     *
     * This method returns a `CommandLineRunner` bean that loads a predefined
     * list of store entities into the database when the application starts.
     *
     * @return a `CommandLineRunner` that loads initial data into the database.
     */
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