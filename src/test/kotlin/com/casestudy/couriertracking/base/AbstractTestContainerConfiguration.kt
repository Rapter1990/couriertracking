package com.casestudy.couriertracking.base

import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * Abstract class for configuring and managing test containers for integration tests.
 *
 * This class sets up a MySQL container for integration testing, providing
 * a real database environment for tests that require database interactions.
 */
@Testcontainers
@SpringJUnitConfig
abstract class AbstractTestContainerConfiguration {

    companion object {
        @JvmStatic
        private val mysqlContainer: MySQLContainer<*> = MySQLContainer("mysql:8.0.33")

        /**
         * Starts the MySQL container before all tests are run.
         */
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            mysqlContainer.withReuse(true)
            mysqlContainer.start()
        }

        /**
         * Configures dynamic properties for the Spring application context using the MySQL container.
         *
         * This method provides the necessary database connection properties (username, password, URL)
         * for the Spring application to connect to the MySQL container.
         *
         * @param dynamicPropertyRegistry the registry to add dynamic properties to.
         */
        @JvmStatic
        @DynamicPropertySource
        fun overrideProps(dynamicPropertyRegistry: DynamicPropertyRegistry) {
            dynamicPropertyRegistry.add("spring.datasource.username", mysqlContainer::getUsername)
            dynamicPropertyRegistry.add("spring.datasource.password", mysqlContainer::getPassword)
            dynamicPropertyRegistry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
        }
    }
}