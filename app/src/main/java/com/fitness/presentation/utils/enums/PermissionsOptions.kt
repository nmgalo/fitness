package com.fitness.presentation.utils.enums

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

enum class PermissionsOptions(val key: String) {
    Location(Manifest.permission.ACCESS_COARSE_LOCATION),
    GPS(Manifest.permission.ACCESS_FINE_LOCATION),
    @RequiresApi(Build.VERSION_CODES.Q)
    BK_LOC(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
}