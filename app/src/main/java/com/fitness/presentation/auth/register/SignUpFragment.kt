package com.fitness.presentation.auth.register

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fitness.R
import com.fitness.databinding.FragmentSignUpBinding
import com.fitness.presentation.common.BaseFragment
import com.fitness.presentation.utils.extensions.collectLatestStateFlowStarted
import com.fitness.presentation.utils.extensions.isEmailValid
import com.fitness.presentation.utils.extensions.isPasswordValid
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_sign_up) {

    override val viewModelClass = SignUpViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentSignUpBinding.bind(view).onViewBind()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectFlows()
    }

    private fun FragmentSignUpBinding.onViewBind() {
        btnLogin.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        btnSignUp.setOnClickListener {
            validateFields(etEmail, etPassword, etConfirmPassword)
        }
    }

    override fun collectFlows() {
        collectLatestStateFlowStarted(viewModel.onUserRegistered) {
            if (it) {
                findNavController().popBackStack()
            }
        }
    }

    private fun validateFields(
        email: TextInputEditText,
        password: TextInputEditText,
        confirmPassword: TextInputEditText
    ) {
        if (!email.text.toString().isEmailValid()) {
            email.error = "Email is not valid"
            email.requestFocus()
            return
        }
        if (!password.text.toString().isPasswordValid()) {
            password.error = "Must be minimum 6 digits"
            password.requestFocus()
            return
        }
        if (confirmPassword.text.toString() != password.text.toString()) {
            confirmPassword.error = "Passwords are not the same"
            confirmPassword.requestFocus()
            return
        }

        viewModel.signUp(email.text.toString(), password.text.toString())
    }


}