package com.fitness.presentation.common.commands

abstract class BaseCommand

sealed class ActivityCommand : BaseCommand() {
    class ShowWarning(val message: String) : ActivityCommand()
    class ToggleLoader(val show: Boolean) : ActivityCommand()
    object NavigateToHome : ActivityCommand()
    object NavigateToOfflineScreen: ActivityCommand()
}