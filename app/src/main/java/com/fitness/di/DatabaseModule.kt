package com.fitness.di

import android.content.Context
import androidx.room.Room
import com.fitness.domain.database.Database
import com.fitness.domain.database.daos.LocationTrackApi
import com.fitness.presentation.utils.constants.DATABASE_NAME
import com.fitness.presentation.utils.constants.LOCATION_TRACK_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    @Named(DATABASE_NAME)
    fun getDatabaseName(@ApplicationContext context: Context): String = LOCATION_TRACK_DB

    @Provides
    @Singleton
    fun provideRoom(
        @ApplicationContext context: Context,
        @Named(DATABASE_NAME) databaseName: String
    ): Database = Room
        .databaseBuilder(context, Database::class.java, databaseName)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideUserSetupDao(database: Database): LocationTrackApi =
        database.getLocationTrackDao()


}