package com.casestudy.couriertracking.courier.exception

/**
 * Exception thrown when a timestamp is before the store's creation date.
 *
 * @param message the detail message explaining the reason for the exception.
 * @constructor Creates a `TimestampBeforeStoreCreateException` with the specified detail message.
 */
class TimestampBeforeStoreCreateException(message: String) : RuntimeException(message)