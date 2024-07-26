package com.casestudy.couriertracking.common.model.dto.response

import com.casestudy.couriertracking.common.model.CustomPage


data class CustomPagingResponse<T>(
        val content: List<T>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalElementCount: Long,
        val totalPageCount: Int
) {
    companion object {
        fun <T, C> fromPage(customPage: CustomPage<C>): CustomPagingResponseBuilder<T> {
            return CustomPagingResponseBuilder<T>().apply {
                pageNumber = customPage.pageNumber
                pageSize = customPage.pageSize
                totalElementCount = customPage.totalElementCount
                totalPageCount = customPage.totalPageCount
            }
        }
    }

    class CustomPagingResponseBuilder<T> {
        var content: List<T> = emptyList()
        var pageNumber: Int = 0
        var pageSize: Int = 0
        var totalElementCount: Long = 0
        var totalPageCount: Int = 0

        fun build(): CustomPagingResponse<T> {
            return CustomPagingResponse(
                    content = content,
                    pageNumber = pageNumber,
                    pageSize = pageSize,
                    totalElementCount = totalElementCount,
                    totalPageCount = totalPageCount
            )
        }
    }
}