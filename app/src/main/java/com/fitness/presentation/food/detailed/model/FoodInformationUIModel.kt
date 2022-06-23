package com.fitness.presentation.food.detailed.model

data class FoodInformationUIModel(
    val veryHealthy: Boolean,
    val preparationMinutes: Int,
    val title: String,
    val image: String,
    val summary: String,
    val dishTypes: List<String>,
    val diets: List<String>
)
