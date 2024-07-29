package com.casestudy.couriertracking.courier.model.mapper

import com.casestudy.couriertracking.courier.model.Courier
import com.casestudy.couriertracking.courier.model.dto.response.CourierResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * Mapper interface for converting between `Courier` and `CourierResponse` objects.
 *
 * This interface uses MapStruct to define mappings between the domain model and the response model.
 *
 * @see Courier
 * @see CourierResponse
 */
@Mapper
interface CourierToCourierResponseMapper {

    /**
     * Maps a `Courier` to a `CourierResponse`.
     *
     * @param source the `Courier` object to map.
     * @return the mapped `CourierResponse` object.
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "lat", source = "lat")
    @Mapping(target = "lng", source = "lng")
    @Mapping(target = "timestamp", source = "timestamp")
    fun map(source: Courier): CourierResponse

    /**
     * Maps a list of `Courier` objects to a list of `CourierResponse` objects.
     *
     * @param sources the list of `Courier` objects to map.
     * @return the list of mapped `CourierResponse` objects.
     */
    fun map(sources: List<Courier>): List<CourierResponse>

    /**
     * Creates an instance of `CourierToCourierResponseMapper`.
     *
     * @return a `CourierToCourierResponseMapper` instance.
     */
    companion object {
        fun initialize(): CourierToCourierResponseMapper {
            return Mappers.getMapper(CourierToCourierResponseMapper::class.java)
        }
    }
}