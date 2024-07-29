package com.casestudy.couriertracking.common.exception

import com.casestudy.couriertracking.base.AbstractRestControllerTest
import com.casestudy.couriertracking.common.model.CustomError
import com.casestudy.couriertracking.courier.exception.CourierNotFoundException
import com.casestudy.couriertracking.courier.exception.StoreFarAwayException
import com.casestudy.couriertracking.courier.exception.StoreNotFoundException
import com.casestudy.couriertracking.courier.exception.TimestampBeforeStoreCreateException
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GlobalExceptionHandlerTest : AbstractRestControllerTest() {

    @InjectMocks
    private lateinit var globalExceptionHandler: GlobalExceptionHandler

    @Test
    fun `given CourierNotFoundException when handleCourierNotFoundException then respond with NotFound`() {
        // Given
        val ex = CourierNotFoundException("Courier not found message")

        val expectedError = CustomError(
                message = "Courier not found message",
                httpStatus = HttpStatus.NOT_FOUND,
                header = CustomError.Header.NOT_FOUND.headerName
        )

        // When
        val responseEntity = globalExceptionHandler.handleCourierNotFoundException(ex)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
        val actualError = responseEntity.body as CustomError
        checkCustomError(expectedError, actualError)
    }

    @Test
    fun `given StoreNotFoundException when handleStoreNotFoundException then respond with NotFound`() {
        // Given
        val ex = StoreNotFoundException("Store not found message")

        val expectedError = CustomError(
                message = "Store not found message",
                httpStatus = HttpStatus.NOT_FOUND,
                header = CustomError.Header.NOT_FOUND.headerName
        )

        // When
        val responseEntity = globalExceptionHandler.handleStoreNotFoundException(ex)

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.statusCode)
        val actualError = responseEntity.body as CustomError
        checkCustomError(expectedError, actualError)
    }

    @Test
    fun `given StoreFarAwayException when handleStoreFarAwayException then respond with BadRequest`() {
        // Given
        val ex = StoreFarAwayException("Store is far away message")

        val expectedError = CustomError(
                message = "Store is far away message",
                httpStatus = HttpStatus.BAD_REQUEST,
                header = CustomError.Header.BAD_REQUEST.headerName
        )

        // When
        val responseEntity = globalExceptionHandler.handleStoreFarAwayException(ex)

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        val actualError = responseEntity.body as CustomError
        checkCustomError(expectedError, actualError)
    }

    @Test
    fun `given TimestampBeforeStoreCreateException when handleTimestampBeforeStoreCreateException then respond with BadRequest`() {
        // Given
        val ex = TimestampBeforeStoreCreateException("Timestamp before store creation message")

        val expectedError = CustomError(
                message = "Timestamp before store creation message",
                httpStatus = HttpStatus.BAD_REQUEST,
                header = CustomError.Header.BAD_REQUEST.headerName
        )

        // When
        val responseEntity = globalExceptionHandler.handleTimestampBeforeStoreCreateException(ex)

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.statusCode)
        val actualError = responseEntity.body as CustomError
        checkCustomError(expectedError, actualError)
    }

    private fun checkCustomError(expectedError: CustomError, actualError: CustomError) {
        assertNotNull(actualError)
        assertNotNull(actualError.time)
        assertEquals(expectedError.header, actualError.header)
        assertEquals(expectedError.isSuccess, actualError.isSuccess)

        expectedError.message?.let {
            assertEquals(it, actualError.message)
        }

        expectedError.subErrors?.let {
            assertEquals(it.size, actualError.subErrors?.size)
            it.firstOrNull()?.let { expectedSubError ->
                val actualSubError = actualError.subErrors?.firstOrNull()
                assertEquals(expectedSubError.message, actualSubError?.message)
                assertEquals(expectedSubError.field, actualSubError?.field)
                assertEquals(expectedSubError.value, actualSubError?.value)
                assertEquals(expectedSubError.type, actualSubError?.type)
            }
        }
    }
}