package com.fitness.domain.recipes

import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val recipesRepo: RecipesRepo
) {
    suspend fun execute(query: String) = recipesRepo.get(query)
}
