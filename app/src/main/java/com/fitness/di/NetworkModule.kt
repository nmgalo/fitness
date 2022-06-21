package com.fitness.di

import android.content.Context
import com.fitness.BuildConfig
import com.fitness.data.common.InternetConnection
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().run {
        addInterceptor { chain ->
            chain.request().newBuilder()
                .addHeader("x-api-key", BuildConfig.RECIPES_API_KEY)
                .build()
                .let(chain::proceed)
        }
        if (BuildConfig.DEBUG)
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        build()
    }

    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val retrofit = Retrofit.Builder()
        retrofit.client(okHttpClient)
        retrofit.baseUrl(BuildConfig.RECIPES_BASE_URL)
        retrofit.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        return retrofit.build()
    }

    @Provides
    fun provideInternetConnectionListener(@ApplicationContext context: Context) =
        InternetConnection(context)
}
