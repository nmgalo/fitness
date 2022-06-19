package com.fitness.presentation.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.presentation.common.commands.ActivityCommand
import com.fitness.presentation.common.commands.CommandHandler
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    private var commandHandler: CommandHandler? = null

    fun setHandler(commandHandler: CommandHandler) {
        this.commandHandler = commandHandler
    }

    fun removeHandler() {
        this.commandHandler = null
    }

    protected fun execute(
        withLoader: Boolean = false,
        callback: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (withLoader)
                toggleLoader(true)
            try {
                if (withLoader)
                    delay(600)
                this.callback()
            } catch (exception: Exception) {
                Log.e("exception", exception.message.toString())
                if (exception !is CancellationException) {
                    showWarning(exception)
                }
            } finally {
                toggleLoader(false)
            }
        }
    }

    private suspend fun toggleLoader(show: Boolean) {
        withContext(Dispatchers.Main) {
            commandHandler?.handleCommand(ActivityCommand.ToggleLoader(show))
        }
    }

    fun showToast(message:String){
        commandHandler?.handleCommand(
            ActivityCommand.ShowWarning(
                message
            )
        )
    }
    private fun showWarning(exception: Exception) {
        val defaultMessage = "Something went wrong"
        val message = if (exception is ApiException)
            exception.message ?: defaultMessage
        else
            defaultMessage
        commandHandler?.handleCommand(
            ActivityCommand.ShowWarning(
                message
            )
        )
    }
}