package com.casestudy.couriertracking.courier.model.mapper

import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.entity.CourierEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * Mapper interface for converting between `CourierEntity` and `Courier` objects.
 *
 * This interface uses MapStruct to define mappings between the entity and domain model.
 *
 * @see CourierEntity
 * @see Courier
 */
@Mapper
interface CourierEntityToCourierMapper {

    /**
     * Maps a `CourierEntity` to a `Courier`.
     *
     * @param source the `CourierEntity` to map.
     * @return the mapped `Courier` object.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "courierId", source = "courierId")
    @Mapping(target = "lat", source = "lat")
    @Mapping(target = "lng", source = "lng")
    @Mapping(target = "storeName", source = "storeName")
    @Mapping(target = "timestamp", source = "timestamp")
    fun map(source: CourierEntity): Courier

    /**
     * Maps a list of `CourierEntity` objects to a list of `Courier` objects.
     *
     * @param sources the list of `CourierEntity` objects to map.
     * @return the list of mapped `Courier` objects.
     */
    fun map(sources: List<CourierEntity>): List<Courier>

    /**
     * Creates an instance of `CourierEntityToCourierMapper`.
     *
     * @return a `CourierEntityToCourierMapper` instance.
     */
    companion object {
        fun initialize(): CourierEntityToCourierMapper {
            return Mappers.getMapper(CourierEntityToCourierMapper::class.java)
        }
    }
}