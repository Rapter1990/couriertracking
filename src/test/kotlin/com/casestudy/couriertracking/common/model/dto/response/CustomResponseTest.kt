package com.casestudy.couriertracking.common.model.dto.response

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class CustomResponseTest {

    @Test
    fun `test errorOf creates CustomResponse with error state`() {
        val errorResponse = "Error occurred"
        val response = CustomResponse.errorOf(errorResponse)

        // Check that the response has the expected values
        assertEquals(HttpStatus.OK, response.httpStatus, "HTTP Status should be OK")
        assertEquals(false, response.isSuccess, "isSuccess should be false for error responses")
        assertEquals(errorResponse, response.response, "Response should contain the error message")
        assertEquals(LocalDateTime.now().toLocalDate(), response.time.toLocalDate(), "Time should be close to current date")
    }

}