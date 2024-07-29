package com.casestudy.couriertracking.courier.exception

/**
 * Exception thrown when a store is not found.
 *
 * @param message the detail message explaining the reason for the exception.
 * @constructor Creates a `StoreNotFoundException` with the specified detail message.
 */
class StoreNotFoundException(message: String) : RuntimeException(message)