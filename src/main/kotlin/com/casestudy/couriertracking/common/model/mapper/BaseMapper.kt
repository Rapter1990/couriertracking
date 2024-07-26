package com.casestudy.couriertracking.common.model.mapper

interface BaseMapper<S, T> {

    /**
     * Maps a single source object to a target object.
     *
     * @param source the source object to map
     * @return the mapped target object
     */
    fun map(source: S): T

    /**
     * Maps a collection of source objects to a list of target objects.
     *
     * @param sources the collection of source objects to map
     * @return the list of mapped target objects
     */
    fun map(sources: Collection<S>): List<T>
}