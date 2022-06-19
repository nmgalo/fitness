package com.fitness.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.fitness.data.preferences.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideMasterKey(@ApplicationContext context: Context): MasterKey =
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context,
        masterKey: MasterKey
    ): SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "UserPreferences",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @Provides
    @Singleton
    fun provideUserPreferences(sharedPreferences: SharedPreferences):
            UserPreferences = UserPreferences(sharedPreferences)


}