package com.casestudy.couriertracking.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OpenApiConfigTest {

    @Test
    fun openApiInfo() {

        // Given
        val openAPIDefinition = OpenApiConfig::class.java.getAnnotation(OpenAPIDefinition::class.java)

        // Then
        assertEquals("1.0.0", openAPIDefinition.info.version)
        assertEquals("couriertracking", openAPIDefinition.info.title)
        assertEquals(
                "Case Study - Courier Tracking with Spring Boot through Kotlin",
                openAPIDefinition.info.description
        )

    }

    @Test
    fun contactInfo() {

        // Given
        val info = OpenApiConfig::class.java.getAnnotation(OpenAPIDefinition::class.java).info
        val contact = info.contact

        // Then
        assertEquals("Sercan Noyan Germiyanoğlu", contact.name)
        assertEquals("https://github.com/Rapter1990/couriertracking/", contact.url)

    }

}