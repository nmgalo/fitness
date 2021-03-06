package com.fitness.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.domain.preferences.CredentialsRepo
import com.fitness.presentation.common.commands.ActivityCommand
import com.fitness.presentation.common.commands.BaseCommand
import com.fitness.presentation.common.commands.CommandHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val credentialsRepo: CredentialsRepo
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

}

