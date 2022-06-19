package com.fitness.presentation.utils.extensions

import androidx.fragment.app.Fragment
import com.fitness.presentation.auth.login.LoginFragment
import com.fitness.presentation.auth.register.SignUpFragment


fun Fragment.hideBottomBar(): Boolean {
    return childFragmentManager.fragments.getOrNull(0) !is LoginFragment &&
            childFragmentManager.fragments.getOrNull(0) !is SignUpFragment
}