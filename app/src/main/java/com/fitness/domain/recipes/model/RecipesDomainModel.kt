package com.fitness.domain.recipes.model

data class RecipesDomainModel(
    val id: Long,
    val title: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val image: String,
    val protein: String
)
