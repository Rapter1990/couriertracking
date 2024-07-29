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

@RestControllerAdvice
class GlobalExceptionHandler {

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
