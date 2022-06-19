package com.fitness.domain.preferences

interface CredentialsRepo {

    suspend fun saveAuthToken(token: String)

    suspend fun getAuthToken(): String

    suspend fun clear()
}