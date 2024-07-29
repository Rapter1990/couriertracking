package com.casestudy.couriertracking.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Unit tests for validating the OpenAPI configuration in the `OpenApiConfig` class.
 *
 * This test class verifies that the OpenAPI annotations and their attributes are correctly
 * configured for the `OpenApiConfig` class. It checks the OpenAPI version, title, description,
 * and contact information.
 */
class OpenApiConfigTest {

    /**
     * Tests that the OpenAPI version, title, and description in the `OpenApiConfig` class
     * are set correctly.
     *
     * This test fetches the `OpenAPIDefinition` annotation from the `OpenApiConfig` class and
     * verifies that the `info` section of the annotation has the expected values for version,
     * title, and description.
     */
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

    /**
     * Tests that the contact information in the `OpenApiConfig` class is set correctly.
     *
     * This test retrieves the contact information from the `OpenAPIDefinition` annotation on
     * the `OpenApiConfig` class and verifies that the contact name and URL are as expected.
     */
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