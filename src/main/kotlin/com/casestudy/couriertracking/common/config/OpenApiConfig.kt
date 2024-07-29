package com.casestudy.couriertracking.common.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

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