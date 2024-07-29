package com.casestudy.couriertracking.courier.model.mapper

import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.dto.response.CourierResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface CourierToCourierResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "lat", source = "lat")
    @Mapping(target = "lng", source = "lng")
    @Mapping(target = "timestamp", source = "timestamp")
    fun map(source: Courier): CourierResponse

    fun map(sources: List<Courier>): List<CourierResponse>

    companion object {
        fun initialize(): CourierToCourierResponseMapper {
            return Mappers.getMapper(CourierToCourierResponseMapper::class.java)
        }
    }
}