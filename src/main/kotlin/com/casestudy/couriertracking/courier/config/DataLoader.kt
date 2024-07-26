package com.casestudy.couriertracking.courier.config

import com.casestudy.couriertracking.courier.model.entity.StoreEntity
import com.casestudy.couriertracking.courier.repository.StoreRepository
import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import lombok.RequiredArgsConstructor
import org.springframework.core.io.ResourceLoader

@Configuration
@RequiredArgsConstructor
class DataLoader(private val resourceLoader: ResourceLoader, private val storeRepository: StoreRepository) {

    @Bean
    fun loadInitialData(): CommandLineRunner {
        return CommandLineRunner {
            val objectMapper = jacksonObjectMapper()
            val resource = resourceLoader.getResource("classpath:stores.json")
            val file = resource.file
            val stores: List<StoreEntity> = objectMapper.readValue(file, object : TypeReference<List<StoreEntity>>() {})
            storeRepository.saveAll(stores)
        }
    }
}
