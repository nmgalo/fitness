package com.fitness.domain.database.models

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_track")
data class LocationTrackModel(
    var img: Bitmap? = null,
    var timestamp: Long?,
    var avgSpeedInKMH: Float?,
    var distanceInMeters: Int?,
    var timeInMillis: Long?,
    var caloriesBurned: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}