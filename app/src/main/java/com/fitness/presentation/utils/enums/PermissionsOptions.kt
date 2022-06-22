package com.fitness.presentation.utils.enums

import android.Manifest

enum class PermissionsOptions(val key: String) {
    Location(Manifest.permission.ACCESS_COARSE_LOCATION),
    GPS(Manifest.permission.ACCESS_FINE_LOCATION)
}