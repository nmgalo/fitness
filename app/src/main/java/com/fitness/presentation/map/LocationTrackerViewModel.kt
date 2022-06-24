package com.fitness.presentation.map

import android.graphics.Bitmap
import android.util.Log
import com.fitness.data.database.LocationTrackRepoImpl
import com.fitness.domain.database.models.LocationTrackModel
import com.fitness.presentation.common.BaseViewModel
import com.fitness.presentation.utils.services.Polyline
import com.fitness.presentation.utils.services.TrackingUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.math.log
import kotlin.math.round

@HiltViewModel
class LocationTrackerViewModel @Inject constructor(
    private val locationTrackRepo: LocationTrackRepoImpl
) : BaseViewModel() {

    private suspend fun insertRun(run: LocationTrackModel) {

        locationTrackRepo.saveOrUpdateData(run)
    }

    fun calculateRun(
        locationModel: Bitmap?,
        pathPoints: MutableList<Polyline>,
        curTimeInMillis: Long
    ) {
        execute {
            var distanceInMeters = 0
            for (polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            val avgSpeed =
                round((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60) * 10) / 10f
            val dateTimestamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters / 1000f) * 80f).toInt()
            val run = LocationTrackModel(
                locationModel,
                dateTimestamp,
                avgSpeed,
                distanceInMeters,
                curTimeInMillis,
                caloriesBurned
            )
            insertRun(run)
        }
    }
}