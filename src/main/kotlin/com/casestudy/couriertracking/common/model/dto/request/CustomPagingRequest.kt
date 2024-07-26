package com.casestudy.couriertracking.common.model.dto.request

import com.casestudy.couriertracking.common.model.CustomPaging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class CustomPagingRequest(
        var pagination: CustomPaging = CustomPaging()
) {

    /**
     * Converts CustomPagingRequest to a Pageable object used for pagination in queries.
     *
     * @return Pageable object configured with page number and page size.
     */
    fun toPageable(): Pageable {
        return PageRequest.of(
                pagination.getZeroBasedPageNumber(),
                pagination.pageSize
        )
    }
}