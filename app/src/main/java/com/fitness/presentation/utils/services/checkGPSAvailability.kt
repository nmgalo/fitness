package com.fitness.presentation.utils.services

import android.content.Context
import android.location.LocationManager

fun checkGPSAvailability(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

}