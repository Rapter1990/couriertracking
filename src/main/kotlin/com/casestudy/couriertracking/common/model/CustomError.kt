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
        API_ERROR("API ERROR"),
        ALREADY_EXIST("ALREADY EXIST"),
        NOT_FOUND("NOT EXIST"),
        VALIDATION_ERROR("VALIDATION ERROR"),
        DATABASE_ERROR("DATABASE ERROR"),
        PROCESS_ERROR("PROCESS ERROR"),
        AUTH_ERROR("AUTH ERROR");
    }
}