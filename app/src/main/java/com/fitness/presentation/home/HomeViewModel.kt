package com.fitness.presentation.home

import com.fitness.domain.database.models.LocationTrackModel
import com.fitness.domain.database.repositories.LocationTrackRepo
import com.fitness.presentation.common.BaseViewModel
import com.fitness.presentation.home.model.LocationUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    locationTrackRepo: LocationTrackRepo
) : BaseViewModel() {


    private val _locations = MutableStateFlow<List<LocationUIModel>>(emptyList())
    val locations = _locations.asStateFlow()

    init {
        execute {
            val res = locationTrackRepo.getData()
            _locations.emit(res.map { it.toUIModel() })
        }
    }


    private fun LocationTrackModel.toUIModel() = LocationUIModel(
        id = this.id?.toLong() ?: 0L,
        image = this.img,
        timestamp = timestamp ?: 0L,
        avgSpeedInKMH = avgSpeedInKMH ?: 0F,
        distanceInMeters = distanceInMeters ?: 0,
        timeInMillis = timeInMillis ?: 0L,
        caloriesBurned = caloriesBurned ?: 0

    )
}