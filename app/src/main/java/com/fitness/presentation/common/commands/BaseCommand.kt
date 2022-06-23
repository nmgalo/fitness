package com.fitness.presentation.common.commands

import android.location.Location
import com.google.android.gms.maps.GoogleMap
import androidx.navigation.NavDirections

abstract class BaseCommand

sealed class ActivityCommand : BaseCommand() {
    class ShowWarning(val message: String) : ActivityCommand()
    class ToggleLoader(val show: Boolean) : ActivityCommand()
    data class Navigate(val destination: NavDirections) : ActivityCommand()
    object NavigateToHome : ActivityCommand()
    class RequestPermission(
        val permissions: Array<String>,
        val resultCallback: (Boolean) -> Unit
    ) : ActivityCommand()

    class GetLocationTracker(
        val map: GoogleMap,
        val resultCallback: (Location) -> Unit = {}
    ) : ActivityCommand()

    object NavigateToOfflineScreen : ActivityCommand()
}