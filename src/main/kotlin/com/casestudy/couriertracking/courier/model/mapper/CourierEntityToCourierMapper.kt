package com.casestudy.couriertracking.courier.model.mapper

import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.entity.CourierEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface CourierEntityToCourierMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "courierId", source = "courierId")
    @Mapping(target = "lat", source = "lat")
    @Mapping(target = "lng", source = "lng")
    @Mapping(target = "storeName", source = "storeName")
    @Mapping(target = "timestamp", source = "timestamp")
    fun map(source: CourierEntity): Courier

    fun map(sources: List<CourierEntity>): List<Courier>

    companion object {
        fun initialize(): CourierEntityToCourierMapper {
            return Mappers.getMapper(CourierEntityToCourierMapper::class.java)
        }
    }
}