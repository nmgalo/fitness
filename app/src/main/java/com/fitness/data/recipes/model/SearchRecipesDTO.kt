package com.fitness.data.recipes.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchRecipesDTO(
    val offset: Int,
    val number: Int,
    val results: List<RecipeDTO>,
    val totalResults: Int
)
