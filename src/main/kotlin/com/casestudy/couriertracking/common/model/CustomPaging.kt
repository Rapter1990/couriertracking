package com.casestudy.couriertracking.common.model

import jakarta.validation.constraints.Min

data class CustomPaging(
        @Min(value = 1, message = "Page number must be bigger than 0")
        var pageNumber: Int = 1,

        @Min(value = 1, message = "Page size must be bigger than 0")
        var pageSize: Int = 1
) {
    /**
     * Returns the zero-based page number for internal processing.
     *
     * @return the zero-based page number derived from the specified page number
     */
    fun getZeroBasedPageNumber(): Int {
        return pageNumber - 1
    }
}