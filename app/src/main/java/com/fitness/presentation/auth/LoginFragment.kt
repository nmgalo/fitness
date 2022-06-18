package com.fitness.presentation.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.fitness.R
import com.fitness.databinding.FragmentLoginBinding
import com.fitness.presentation.common.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        FragmentLoginBinding.bind(view).onViewBind()
    }

    private fun FragmentLoginBinding.onViewBind() {
        signInButton.setOnClickListener {
            loader.showLoader()
            signInButton.isClickable = false
            val email = userEmailLayout.editText?.text.toString()
            val password = userPasswordLayout.editText?.text.toString()
            if (email.isNotEmpty() || password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity!!) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                        } else Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                        loader.showLoader(false)
                        signInButton.isClickable = true
                    }
            } else {
                loader.showLoader(false)
                signInButton.isClickable = true
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
