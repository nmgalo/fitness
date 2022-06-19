package com.fitness.data.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPreferences(val sharedPreferences: SharedPreferences) {

    suspend fun saveToken(key: String, token: String) {
        withContext(Dispatchers.Default) {
            with(sharedPreferences.edit()) {
                putString(key, token)
                apply()
            }
        }
    }

    suspend fun getToken(key: String): String {
        return withContext(Dispatchers.Default) {
            sharedPreferences.getString(key, String()) ?: String()
        }
    }

    suspend fun clearToken(key: String) {
        return withContext(Dispatchers.Default) {
            with(sharedPreferences.edit()) {
                remove(key)
                apply()
            }
        }
    }
}