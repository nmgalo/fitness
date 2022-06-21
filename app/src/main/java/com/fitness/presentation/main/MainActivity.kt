package com.fitness.presentation.main

import android.os.Bundle
import android.widget.Toast
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
import com.fitness.presentation.utils.dialogs.LoaderAnimDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var loaderDialog: LoaderAnimDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.container)
        binding.navigation.setupWithNavController(navController)
        checkCurrentFragment(navController)

        viewModel.activityCommandSubject
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(this::onCommandReceived)
            .launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()
        viewModel.initializeSessionStatus()
        viewModel.initialNetworkListener()
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
                findNavController(R.id.container).navigate(R.id.action_global_homeFragment)
            }
            ActivityCommand.NavigateToOfflineScreen ->
                findNavController(R.id.container).navigate(R.id.action_global_offlineFragment)
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
                R.id.offlineFragment -> false
                else -> true
            }
        }
    }

}