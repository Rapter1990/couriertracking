package com.casestudy.couriertracking.common.model

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

/**
 * Represents a custom error response structure for REST APIs.
 */
data class CustomError(
        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val message: String? = null,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val subErrors: List<CustomSubError>? = null,

        val time: LocalDateTime = LocalDateTime.now(),
        val httpStatus: HttpStatus,
        val header: String,
        val isSuccess: Boolean = false
) {
    /**
     * Represents a sub-error with specific details.
     */
    data class CustomSubError(
            val message: String,
            val field: String,

            @field:JsonInclude(JsonInclude.Include.NON_NULL)
            val value: Any? = null,

            @field:JsonInclude(JsonInclude.Include.NON_NULL)
            val type: String? = null
    )

    /**
     * Enumerates common error headers for categorizing errors.
     */
    enum class Header(val headerName: String) {
        NOT_FOUND("NOT EXIST"),
        BAD_REQUEST("BAD REQUEST"),
        PROCESS_ERROR("PROCESS ERROR"),
    }
}