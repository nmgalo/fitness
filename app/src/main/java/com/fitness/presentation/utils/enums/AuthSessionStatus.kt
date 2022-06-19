package com.fitness.presentation.utils.enums


sealed class AuthSessionStatus {
    object Unauthorized : AuthSessionStatus()
    object Authorized : AuthSessionStatus()

}