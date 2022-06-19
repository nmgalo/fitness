package com.fitness.presentation.auth.register

import com.fitness.presentation.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
) : BaseViewModel() {

    private val _onUserLRegistered = MutableSharedFlow<Boolean>()
    val onUserRegistered = _onUserLRegistered.asSharedFlow()

    fun signUp(email: String, password: String) {
        execute(withLoader = true) {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        execute {
                            showToast("Successfully Registered! Please Login")
                            _onUserLRegistered.emit(true)
                        }
                    } else {
                        showToast("Authentication failed.")
                    }
                }
        }
    }

}