package com.fitness.domain.database.repositories

import com.fitness.domain.database.models.LocationTrackModel

interface LocationTrackRepo {

    suspend fun saveOrUpdateData(locationTrackModel: LocationTrackModel)

    suspend fun clearData()

    suspend fun getData(): List<LocationTrackModel>

}