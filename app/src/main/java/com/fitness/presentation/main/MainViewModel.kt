package com.fitness.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.data.common.InternetConnection
import com.fitness.domain.preferences.CredentialsRepo
import com.fitness.presentation.common.commands.ActivityCommand
import com.fitness.presentation.common.commands.BaseCommand
import com.fitness.presentation.common.commands.CommandHandler
import com.fitness.presentation.utils.enums.AuthSessionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val credentialsRepo: CredentialsRepo,
    private val internetConnection: InternetConnection
) : ViewModel(), CommandHandler {

    private val _activityCommandSubject: MutableSharedFlow<ActivityCommand> = MutableSharedFlow()
    val activityCommandSubject = _activityCommandSubject.asSharedFlow()

    override fun handleCommand(command: BaseCommand) {
        when (command) {
            is ActivityCommand -> {
                viewModelScope.launch {
                    _activityCommandSubject.emit(command)
                }
            }
        }
    }

    fun initializeSessionStatus() {
        viewModelScope.launch {
            val isAuthenticated = credentialsRepo.getAuthToken().isNotEmpty()
            if (isAuthenticated)
                _activityCommandSubject.emit(ActivityCommand.NavigateToHome)
        }
    }

    fun initialNetworkListener() {
        viewModelScope.launch {
            _activityCommandSubject.emit(ActivityCommand.NavigateToOfflineScreen)
        }
    }
}

