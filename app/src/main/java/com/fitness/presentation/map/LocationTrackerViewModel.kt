package com.fitness.presentation.map

import com.fitness.data.database.LocationTrackRepoImpl
import com.fitness.domain.database.models.LocationTrackModel
import com.fitness.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationTrackerViewModel @Inject constructor(
    private val locationTrackRepo: LocationTrackRepoImpl
) : BaseViewModel() {



    fun insertRun(locationModel: LocationTrackModel) {
        execute {
            locationTrackRepo.saveOrUpdateData(locationModel)
        }
    }
}