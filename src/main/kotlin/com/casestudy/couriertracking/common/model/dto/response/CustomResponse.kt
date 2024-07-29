package com.casestudy.couriertracking.common.model.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class CustomResponse<T>(
        val time: LocalDateTime = LocalDateTime.now(),
        val httpStatus: HttpStatus,
        val isSuccess: Boolean,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val response: T? = null
) {
    companion object {
        val SUCCESS: CustomResponse<Void> = CustomResponse(
                httpStatus = HttpStatus.OK,
                isSuccess = true
        )

        fun <T> successOf(response: T): CustomResponse<T> {
            return CustomResponse(
                    httpStatus = HttpStatus.OK,
                    isSuccess = true,
                    response = response
            )
        }

        fun <T> errorOf(response: T): CustomResponse<T> {
            return CustomResponse(
                    httpStatus = HttpStatus.OK,
                    isSuccess = false,
                    response = response
            )
        }

    }
}