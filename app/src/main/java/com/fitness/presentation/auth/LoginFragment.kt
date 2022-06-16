package com.fitness.presentation.auth

import android.os.Bundle
import android.view.View
import com.fitness.R
import com.fitness.databinding.FragmentLoginBinding
import com.fitness.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentLoginBinding.bind(view).onViewBind()
    }

    private fun FragmentLoginBinding.onViewBind() {
//        TODO("Write better TODO xDD")
    }
}
