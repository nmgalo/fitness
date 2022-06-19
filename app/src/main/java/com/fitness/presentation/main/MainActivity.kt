package com.fitness.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fitness.R
import com.fitness.databinding.ActivityMainBinding
import com.fitness.presentation.auth.login.LoginFragmentDirections
import com.fitness.presentation.common.commands.ActivityCommand
import com.fitness.presentation.utils.dialogs.LoaderAnimDialog
import com.fitness.presentation.utils.extensions.hideBottomBar
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
        checkCurrentFragment()

        viewModel.activityCommandSubject
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(this::onCommandReceived)
            .launchIn(lifecycleScope)
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
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController(R.id.container)
                    .navigate(action)
            }
        }
    }

    private fun toggleLoader(show: Boolean) {
        loaderDialog?.dismiss()
        loaderDialog = null
        if (show) {
            loaderDialog = LoaderAnimDialog(this).showDialog()
        }
    }

    private fun checkCurrentFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container)
        binding.navigation.isVisible = navHostFragment?.hideBottomBar() == true
        navHostFragment?.childFragmentManager?.addOnBackStackChangedListener {
            binding.navigation.isVisible = navHostFragment.hideBottomBar() == true
        }
    }

}