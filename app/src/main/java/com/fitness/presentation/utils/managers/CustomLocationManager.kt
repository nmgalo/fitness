package com.fitness.presentation.utils.managers

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng


class CustomLocationManager(val context: Activity) {

    @SuppressLint("MissingPermission")
    fun getCurrentGpsLocation(mMap: GoogleMap, callback: (Location) -> Unit) {
        var lastLocation: Location? = null
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.isMyLocationEnabled = true
        mMap.setOnMyLocationButtonClickListener {
            lastLocation?.let {
                callback.invoke(it)
            }
            false
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }
}
