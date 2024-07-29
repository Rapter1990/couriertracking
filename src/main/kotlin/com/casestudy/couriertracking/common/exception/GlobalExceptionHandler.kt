package com.casestudy.couriertracking.common.exception

import com.casestudy.couriertracking.common.model.CustomError
import com.casestudy.couriertracking.courier.exception.CourierNotFoundException
import com.casestudy.couriertracking.courier.exception.StoreFarAwayException
import com.casestudy.couriertracking.courier.exception.StoreNotFoundException
import com.casestudy.couriertracking.courier.exception.TimestampBeforeStoreCreateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * A global exception handler for handling custom and generic exceptions.
 *
 * This class uses Spring Boot's `@RestControllerAdvice` to provide global exception handling
 * for REST controllers. Specific exception handler methods are defined to handle custom exceptions
 * and return appropriate responses.
 *
 * @constructor Creates a GlobalExceptionHandler instance.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handles `CourierNotFoundException` and returns a `ResponseEntity` with a custom error message.
     *
     * @param ex the `CourierNotFoundException` instance.
     * @return a `ResponseEntity` containing a `CustomError` object with NOT_FOUND status.
     */
    @ExceptionHandler(CourierNotFoundException::class)
    fun handleCourierNotFoundException(ex: CourierNotFoundException): ResponseEntity<CustomError> {
        return ResponseEntity(
                CustomError(
                        message = ex.message,
                        httpStatus = HttpStatus.NOT_FOUND,
                        header = CustomError.Header.NOT_FOUND.headerName
                ),
                HttpStatus.NOT_FOUND
        )
    }

    /**
     * Handles `StoreNotFoundException` and returns a `ResponseEntity` with a custom error message.
     *
     * @param ex the `StoreNotFoundException` instance.
     * @return a `ResponseEntity` containing a `CustomError` object with NOT_FOUND status.
     */
    @ExceptionHandler(StoreNotFoundException::class)
    fun handleStoreNotFoundException(ex: StoreNotFoundException): ResponseEntity<CustomError> {
        return ResponseEntity(
                CustomError(
                        message = ex.message,
                        httpStatus = HttpStatus.NOT_FOUND,
                        header = CustomError.Header.NOT_FOUND.headerName
                ),
                HttpStatus.NOT_FOUND
        )
    }

    /**
     * Handles `StoreFarAwayException` and returns a `ResponseEntity` with a custom error message.
     *
     * @param ex the `StoreFarAwayException` instance.
     * @return a `ResponseEntity` containing a `CustomError` object with BAD_REQUEST status.
     */
    @ExceptionHandler(StoreFarAwayException::class)
    fun handleStoreFarAwayException(ex: StoreFarAwayException): ResponseEntity<CustomError> {
        return ResponseEntity(
                CustomError(
                        message = ex.message,
                        httpStatus = HttpStatus.BAD_REQUEST,
                        header = CustomError.Header.BAD_REQUEST.headerName
                ),
                HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handles `TimestampBeforeStoreCreateException` and returns a `ResponseEntity` with a custom error message.
     *
     * @param ex the `TimestampBeforeStoreCreateException` instance.
     * @return a `ResponseEntity` containing a `CustomError` object with BAD_REQUEST status.
     */
    @ExceptionHandler(TimestampBeforeStoreCreateException::class)
    fun handleTimestampBeforeStoreCreateException(ex: TimestampBeforeStoreCreateException): ResponseEntity<CustomError> {
        return ResponseEntity(
                CustomError(
                        message = ex.message,
                        httpStatus = HttpStatus.BAD_REQUEST,
                        header = CustomError.Header.BAD_REQUEST.headerName
                ),
                HttpStatus.BAD_REQUEST
        )
    }

    /**
     * Handles generic exceptions and returns a `ResponseEntity` with a custom error message.
     *
     * @param ex the `Exception` instance.
     * @return a `ResponseEntity` containing a `CustomError` object with INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<CustomError> {
        return ResponseEntity(
                CustomError(
                        message = "Internal server error",
                        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                        header = CustomError.Header.PROCESS_ERROR.headerName
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

}
