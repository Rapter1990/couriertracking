package com.casestudy.couriertracking.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for OpenAPI documentation.
 *
 * This class uses Spring Boot's `@Configuration` annotation to define
 * a configuration for OpenAPI documentation. The `@OpenAPIDefinition`
 * annotation is used to provide metadata about the API.
 *
 * @constructor Creates an OpenApiConfig instance.
 */
@Configuration
@OpenAPIDefinition(
        info = Info(
                contact = Contact(
                        name = "Sercan Noyan Germiyanoğlu",
                        url = "https://github.com/Rapter1990/couriertracking/"
                ),
                description = "Case Study - Courier Tracking with Spring Boot through Kotlin",
                title = "couriertracking",
                version = "1.0.0"
        )
)
class OpenApiConfig