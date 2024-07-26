package com.casestudy.couriertracking.common.model

import org.springframework.data.domain.Page

data class CustomPage<T>(
        val content: List<T>,
        val pageNumber: Int,
        val pageSize: Int,
        val totalElementCount: Long,
        val totalPageCount: Int
) {
    companion object {
        /**
         * Constructs a CustomPage instance from a domain model page.
         *
         * @param domainModels the list of domain models in the page
         * @param page         the Page object containing pagination information
         * @param <C>          the type of domain model in the page
         * @param <X>          the type of page
         * @return a CustomPage instance mapped from the provided Page
         */
        fun <C, X> fromPage(domainModels: List<C>, page: Page<X>): CustomPage<C> {
            return CustomPage(
                    content = domainModels,
                    pageNumber = page.number + 1,
                    pageSize = page.size,
                    totalElementCount = page.totalElements,
                    totalPageCount = page.totalPages
            )
        }
    }
}