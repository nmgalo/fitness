package com.fitness.data.recipes

import com.fitness.data.RecipesService
import com.fitness.domain.recipes.RecipesRepo
import com.fitness.domain.recipes.model.RecipesDomainModel
import com.fitness.domain.recipes.model.information.FoodInformationDomainModel
import javax.inject.Inject

class RecipesRepoImpl @Inject constructor(
    private val recipesService: RecipesService
) : RecipesRepo {
    override suspend fun get(query: String): List<RecipesDomainModel> =
        recipesService.search(query).results.map { it.toDomainModel() }

    override suspend fun getInformationById(foodId: Long): FoodInformationDomainModel =
        recipesService.getInformation(foodId).toDomainModel()
}

