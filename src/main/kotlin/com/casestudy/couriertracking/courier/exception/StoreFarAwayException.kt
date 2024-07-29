package com.casestudy.couriertracking.courier.exception

/**
 * Exception thrown when a store is too far away.
 *
 * @param message the detail message explaining the reason for the exception.
 * @constructor Creates a `StoreFarAwayException` with the specified detail message.
 */
class StoreFarAwayException(message: String) : RuntimeException(message)