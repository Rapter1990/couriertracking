package com.casestudy.couriertracking.courier.exception

/**
 * Exception thrown when a courier is not found.
 *
 * @param message the detail message explaining the reason for the exception.
 * @constructor Creates a `CourierNotFoundException` with the specified detail message.
 */
class CourierNotFoundException(message: String) : RuntimeException(message)