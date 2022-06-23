package com.fitness.data.database

import com.fitness.domain.database.daos.LocationTrackApi
import com.fitness.domain.database.models.LocationTrackModel
import com.fitness.domain.database.repositories.LocationTrackRepo
import javax.inject.Inject

class LocationTrackRepoImpl @Inject constructor(
    private val locationTracker: LocationTrackApi
) : LocationTrackRepo {
    override suspend fun saveOrUpdateData(locationTrackModel: LocationTrackModel) {
        locationTracker.saveOrUpdateData(locationTrackModel)
    }

    override suspend fun clearData() {
        locationTracker.clearData()
    }

    override suspend fun getData(): List<LocationTrackModel> {
        return locationTracker.getData()
    }

}