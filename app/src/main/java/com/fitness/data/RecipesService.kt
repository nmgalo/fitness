package com.fitness.data

import com.fitness.data.recipes.model.SearchRecipesDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipesService {

    @GET("recipes/complexSearch")
    suspend fun search(@Query("query") query: String): SearchRecipesDTO
}
