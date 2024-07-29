package com.casestudy.couriertracking.base

import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringJUnitConfig
abstract class AbstractTestContainerConfiguration {

    companion object {
        @JvmStatic
        private val mysqlContainer: MySQLContainer<*> = MySQLContainer("mysql:8.0.33")

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            mysqlContainer.withReuse(true)
            mysqlContainer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun overrideProps(dynamicPropertyRegistry: DynamicPropertyRegistry) {
            dynamicPropertyRegistry.add("spring.datasource.username", mysqlContainer::getUsername)
            dynamicPropertyRegistry.add("spring.datasource.password", mysqlContainer::getPassword)
            dynamicPropertyRegistry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
        }
    }
}