package com.fitness.presentation.common.commands

import androidx.navigation.NavDirections

abstract class BaseCommand

sealed class ActivityCommand : BaseCommand() {
    class ShowWarning(val message: String) : ActivityCommand()
    class ToggleLoader(val show: Boolean) : ActivityCommand()
    data class Navigate(val destination: NavDirections) : ActivityCommand()
    object NavigateToHome : ActivityCommand()
    object NavigateToOfflineScreen : ActivityCommand()
}