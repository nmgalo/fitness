package com.fitness.presentation.auth.login

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.fitness.R
import com.fitness.databinding.FragmentLoginBinding
import com.fitness.presentation.common.BaseFragment
import com.fitness.presentation.utils.extensions.collectSharedFlowStarted
import com.fitness.presentation.utils.extensions.isEmailValid
import com.fitness.presentation.utils.extensions.isPasswordValid
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    override val viewModelClass = LoginViewModel::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentLoginBinding.bind(view).onViewBind()
    }

    override fun collectFlows() {
        super.collectFlows()
        collectSharedFlowStarted(viewModel.onUserLoggedIn) {
            if (it) {
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                requireView().findNavController().navigate(action)
            }
        }
    }

    private fun FragmentLoginBinding.onViewBind() {

        btnLogin.setOnClickListener {
            validateFields(etEmail, etPassword)
        }

        btnSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            requireView().findNavController().navigate(action)
        }

    }

    private fun validateFields(email: TextInputEditText, password: TextInputEditText) {
        if (!email.text.toString().isEmailValid()) {
            email.error = "Email is not valid"
            email.requestFocus()
            return
        }
        if (!password.text.toString().isPasswordValid()) {
            password.error = "Enter valid password"
            password.requestFocus()
            return
        }
        viewModel.login(email = email.text.toString(), password = password.text.toString())
    }


}
