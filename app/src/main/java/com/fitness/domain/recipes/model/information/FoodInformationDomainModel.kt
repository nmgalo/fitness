package com.fitness.domain.recipes.model.information

data class FoodInformationDomainModel(
    val veryHealthy: Boolean,
    val preparationMinutes: Int,
    val title: String,
    val image: String,
    val summary: String,
    val dishTypes: List<String>,
    val diets: List<String>
)
