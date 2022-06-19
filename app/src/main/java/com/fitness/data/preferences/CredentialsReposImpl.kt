package com.fitness.data.preferences

import com.fitness.domain.preferences.CredentialsRepo
import com.fitness.presentation.utils.constants.AUTH_TOKEN
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialsRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences
) : CredentialsRepo {

    override suspend fun saveAuthToken(token: String) {
        userPreferences.saveToken(AUTH_TOKEN, token)
    }

    override suspend fun getAuthToken(): String = userPreferences.sharedPreferences
        .getString(AUTH_TOKEN, String()) ?: String()

    override suspend fun clear() {
        userPreferences.clearToken(AUTH_TOKEN)
    }

}
