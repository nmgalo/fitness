package com.fitness.di

import com.fitness.data.RecipesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideFoodService(retrofit: Retrofit): RecipesService = retrofit.create()
}
