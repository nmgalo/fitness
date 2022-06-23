package com.fitness.domain.database.daos

import com.fitness.domain.database.models.LocationTrackModel

interface LocationTrackApi {

    suspend fun saveOrUpdateData(locationTrackModel: LocationTrackModel)

    suspend fun clearData()

    suspend fun getData(): List<LocationTrackModel>

}