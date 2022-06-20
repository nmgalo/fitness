package com.fitness.di

import com.fitness.data.preferences.CredentialsRepoImpl
import com.fitness.data.recipes.RecipesRepoImpl
import com.fitness.domain.preferences.CredentialsRepo
import com.fitness.domain.recipes.RecipesRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRecipesRepo(recipesRepo: RecipesRepoImpl): RecipesRepo

    @Binds
    @Singleton
    abstract fun provideCredentialsRepo(recipesRepo: CredentialsRepoImpl): CredentialsRepo
}
