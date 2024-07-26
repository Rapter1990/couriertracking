package com.casestudy.couriertracking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The main entry point for the Courier Tracking Spring Boot application.
 * This class is annotated with `@SpringBootApplication`, which indicates
 * a configuration class that declares one or more `@Bean` methods and also
 * triggers auto-configuration and component scanning.
 */
@SpringBootApplication
class CouriertrackingApplication

/**
 * The main function which serves as the entry point for the Kotlin application.
 * This function uses `runApplication` to launch the Spring Boot application.
 *
 * @param args command-line arguments passed to the application
 */
fun main(args: Array<String>) {
	runApplication<CouriertrackingApplication>(*args)
}
