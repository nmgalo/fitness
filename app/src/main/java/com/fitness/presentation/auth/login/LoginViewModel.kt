package com.fitness.presentation.auth.login

import com.fitness.domain.preferences.CredentialsRepo
import com.fitness.presentation.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val credentialsRepo: CredentialsRepo
) : BaseViewModel() {

    private val _onUserLoggedIn = MutableSharedFlow<Boolean>()
    val onUserLoggedIn = _onUserLoggedIn.asSharedFlow()

    fun login(email: String, password: String) {
        execute(withLoader = true) {
            val auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.uid?.let { saveUserToken(it) }
                    } else {
                        showToast("User not found")
                    }
                }
        }
    }

    private fun saveUserToken(token: String) {
        execute {
            credentialsRepo.saveAuthToken(token)
            _onUserLoggedIn.emit(true)
        }
    }

}