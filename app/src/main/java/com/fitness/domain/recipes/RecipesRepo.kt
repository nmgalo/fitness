package com.fitness.domain.recipes

import com.fitness.domain.recipes.model.RecipesDomainModel
import com.fitness.domain.recipes.model.information.FoodInformationDomainModel

interface RecipesRepo {
    suspend fun get(query: String): Iterable<RecipesDomainModel>
    suspend fun getInformationById(foodId: Long): FoodInformationDomainModel
}
