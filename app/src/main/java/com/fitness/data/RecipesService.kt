package com.fitness.data

import com.fitness.data.recipes.model.SearchRecipesDTO
import com.fitness.data.recipes.model.information.FoodInformationDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesService {

    @GET("recipes/complexSearch")
    suspend fun search(@Query("query") query: String): SearchRecipesDTO

    @GET("recipes/{foodId}/information")
    suspend fun getInformation(@Path("foodId") foodId: Long): FoodInformationDTO
}
