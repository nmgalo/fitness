package com.fitness.presentation.utils.extensions

import android.text.TextUtils
import android.util.Patterns

fun String.isEmailValid() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun String.isPasswordValid() =
    !TextUtils.isEmpty(this) && this.length >= 6