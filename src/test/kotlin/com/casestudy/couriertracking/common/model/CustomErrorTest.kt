package com.casestudy.couriertracking.common.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CustomErrorTest {

    @Test
    fun `test CustomSubError creation with all fields`() {
        val subError = CustomError.CustomSubError(
                message = "Field is required",
                field = "username",
                value = "user123",
                type = "ValidationError"
        )

        assertEquals("Field is required", subError.message, "Message should match")
        assertEquals("username", subError.field, "Field should match")
        assertEquals("user123", subError.value, "Value should match")
        assertEquals("ValidationError", subError.type, "Type should match")
    }

}