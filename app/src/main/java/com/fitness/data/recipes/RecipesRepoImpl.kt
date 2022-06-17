package com.fitness.data.recipes

import com.fitness.data.RecipesService
import com.fitness.domain.recipes.RecipesRepo
import com.fitness.domain.recipes.model.RecipesDomainModel
import javax.inject.Inject

class RecipesRepoImpl @Inject constructor(
    private val recipesService: RecipesService
) : RecipesRepo {
    override suspend fun get(query: String): List<RecipesDomainModel> =
        recipesService.search(query).results.map { it.toDomainModel() }
}
