package com.casestudy.couriertracking.base

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

/**
 * Base class for REST controller tests with Spring Boot and MockMvc.
 *
 * This abstract class sets up the testing environment for REST controllers using
 * Spring Boot. It includes configuration for `MockMvc` to perform HTTP requests
 * and `ObjectMapper` for JSON serialization and deserialization.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
abstract class AbstractRestControllerTest : AbstractTestContainerConfiguration() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper
}