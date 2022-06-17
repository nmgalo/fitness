package com.fitness.domain.recipes

import com.fitness.domain.recipes.model.RecipesDomainModel

interface RecipesRepo {
    suspend fun get(query: String): Iterable<RecipesDomainModel>
}
