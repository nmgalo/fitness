package com.fitness.presentation.map

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.fitness.R
import com.fitness.databinding.FragmentLocationTrackerBinding
import com.fitness.domain.database.models.LocationTrackModel
import com.fitness.presentation.common.BaseFragment
import com.fitness.presentation.common.commands.ActivityCommand
import com.fitness.presentation.utils.constants.ACTION_PAUSE_SERVICE
import com.fitness.presentation.utils.constants.ACTION_START_OR_RESUME_SERVICE
import com.fitness.presentation.utils.constants.ACTION_STOP_SERVICE
import com.fitness.presentation.utils.services.Polyline
import com.fitness.presentation.utils.services.TrackingService
import com.fitness.presentation.utils.services.TrackingUtility
import com.fitness.presentation.utils.services.sendCommandToService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.math.round


@AndroidEntryPoint
class LocationTrackerFragment :
    BaseFragment<LocationTrackerViewModel>(R.layout.fragment_location_tracker) {

    override val viewModelClass = LocationTrackerViewModel::class
    private var googleMap: GoogleMap? = null
    private var pathPoints = mutableListOf<Polyline>()
    private var isTracking = false
    private var curTimeInMillis = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentLocationTrackerBinding.bind(view).onViewBind()
    }

    private fun FragmentLocationTrackerBinding.onViewBind() {
        initMap()
        cvStart.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE, requireContext())
            cvStart.isVisible = false
            cvPause.isVisible = true
            cvStop.isVisible = true
        }
        cvStop.setOnClickListener {
            cvStart.isVisible = true
            cvPause.isVisible = false
            cvStop.isVisible = false
            zoomToSeeWholeTrack(this)
            endRunAndSaveToDb()
            sendCommandToService(ACTION_STOP_SERVICE, requireContext())
        }
        cvPause.setOnClickListener {
            if (isTracking){
                tvPause.text = getString(R.string.resume)
                sendCommandToService(ACTION_PAUSE_SERVICE, requireContext())
            }
            else{
                tvPause.text = getString(R.string.pause)
                sendCommandToService(ACTION_START_OR_RESUME_SERVICE, requireContext())
            }
        }
        subscribeToObservers(this)
    }

    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(R.color.colorPrimary)
                .width(8f)
                .addAll(polyline)
            googleMap?.addPolyline(polylineOptions)
        }
    }

    private fun zoomToSeeWholeTrack(binding: FragmentLocationTrackerBinding) {
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoints) {
            for (pos in polyline) {
                bounds.include(pos)
            }
        }

//        googleMap?.moveCamera(
//            CameraUpdateFactory.newLatLngBounds(
//                bounds.build(),
//                binding.map.width,
//                binding.map.height,
//                (binding.map.height * 0.05f).toInt()
//            )
//        )
    }

    private fun endRunAndSaveToDb() {
        googleMap?.snapshot { bmp ->
            var distanceInMeters = 0
            for (polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            val avgSpeed =
                round((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60) * 10) / 10f
            val dateTimestamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters / 1000f) * 80f).toInt()
            val run = LocationTrackModel(
                bmp,
                dateTimestamp,
                avgSpeed,
                distanceInMeters,
                curTimeInMillis,
                caloriesBurned
            )
            viewModel.insertRun(run)
            Toast.makeText(requireContext(), "Run saved successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun subscribeToObservers(binding: FragmentLocationTrackerBinding) {
        TrackingService.isTracking.observe(viewLifecycleOwner) {
            updateTracking(it, binding)
        }

        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        }

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner) {
            curTimeInMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis)
            binding.tvStopwatch.text = formattedTime
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOptions = PolylineOptions()
                .color(R.color.colorPrimary)
                .width(10f)
                .add(preLastLatLng)
                .add(lastLatLng)
            googleMap?.addPolyline(polylineOptions)
        }
    }

    private fun updateTracking(isTracking: Boolean, binding: FragmentLocationTrackerBinding) {
        this.isTracking = isTracking
        if (!isTracking) {
//            binding.cvStart.isVisible = true
//            binding.tvStart.text = "Resume"
        } else {
//            binding.tvStart.text = "Start"
            binding.cvStart.isVisible = false
            binding.cvStop.isVisible = true
            binding.tvStopwatch.isVisible = true
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            googleMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    15f
                )
            )
        }
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            this.googleMap = googleMap
            postActivityCommand(
                ActivityCommand.GetLocationTracker(googleMap) {}
            )
            addAllPolylines()
        }
    }
}