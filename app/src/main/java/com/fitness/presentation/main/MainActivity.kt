package com.fitness.presentation.main

import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fitness.R
import com.fitness.databinding.ActivityMainBinding
import com.fitness.presentation.common.commands.ActivityCommand
import com.fitness.presentation.utils.constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.fitness.presentation.utils.dialogs.LoaderAnimDialog
import com.fitness.presentation.utils.dialogs.PermissionMessageDialog
import com.fitness.presentation.utils.enums.PermissionsOptions
import com.fitness.presentation.utils.extensions.goToSettings
import com.fitness.presentation.utils.extensions.isPermissionBlocked
import com.fitness.presentation.utils.managers.CustomLocationManager
import com.fitness.presentation.utils.services.checkGPSAvailability
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var loaderDialog: LoaderAnimDialog? = null
    private lateinit var latestPermissionCallback: (Boolean) -> Unit

    private val locationManager = CustomLocationManager(this)
    private lateinit var updatedMapLocationCallback: (Location) -> Unit

    private val askPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val item = result.keys.first() to result.values.first()
            if (!item.second && isPermissionBlocked(item.first)) {
                PermissionMessageDialog(this).showDialog() {
                    goToSettings(this)
                }
            }
            latestPermissionCallback.invoke(item.second)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.container)
        binding.navigation.setupWithNavController(navController)
        checkCurrentFragment(navController)

        navigateToTrackingFragment(intent)

        viewModel.activityCommandSubject
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(this::onCommandReceived)
            .launchIn(lifecycleScope)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragment(intent)
    }

    override fun onStart() {
        super.onStart()
        viewModel.initializeSessionStatus()
    }

    private fun onCommandReceived(command: ActivityCommand) {
        when (command) {
            is ActivityCommand.ShowWarning -> {
                Toast.makeText(this, command.message, Toast.LENGTH_SHORT).show()
            }
            is ActivityCommand.ToggleLoader -> {
                toggleLoader(command.show)
            }
            ActivityCommand.NavigateToHome -> {
                if (intent?.action != ACTION_SHOW_TRACKING_FRAGMENT)
                    findNavController(R.id.container).navigate(R.id.action_global_homeFragment)
            }
            is ActivityCommand.RequestPermission -> {
                askPermission.launch(command.permissions)
                latestPermissionCallback = command.resultCallback
            }
            is ActivityCommand.GetLocationTracker -> {
                val locations = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    arrayOf(
                        PermissionsOptions.Location.key,
                        PermissionsOptions.GPS.key,
                    )
                } else {
                    arrayOf(
                        PermissionsOptions.Location.key,
                        PermissionsOptions.GPS.key,
                        PermissionsOptions.BK_LOC.key,
                    )
                }
                askPermission.launch(locations)
                onLocationPermissionReceived { granted ->
                    if (granted) {
                        getCurrentGpsLocation(command.map)
                        updatedMapLocationCallback = command.resultCallback
                    }
                }
            }
        }
    }

    private fun onLocationPermissionReceived(callback: (Boolean) -> Unit) {
        latestPermissionCallback = callback
    }

    private fun getCurrentGpsLocation(mMap: GoogleMap) {
        checkGPSAvailability(this)
        locationManager.getCurrentGpsLocation(mMap) {
            updatedMapLocationCallback.invoke(it)
        }
    }

    private fun toggleLoader(show: Boolean) {
        loaderDialog?.dismiss()
        loaderDialog = null
        if (show) {
            loaderDialog = LoaderAnimDialog(this).showDialog()
        }
    }

    private fun checkCurrentFragment(navController: NavController) {
        binding.navigation.isVisible = false
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navigation.isVisible = when (destination.id) {
                R.id.loginFragment -> false
                R.id.signUpFragment -> false
                else -> true
            }
        }
    }

    private fun navigateToTrackingFragment(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            findNavController(R.id.container).navigate(R.id.action_global_trackingFragment)
        }

    }

}