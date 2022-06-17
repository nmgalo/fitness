package com.fitness.data.recipes.model

import com.fitness.domain.recipes.model.RecipesDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val id: Long,
    val title: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val image: String,
    val imageType: String,
    val protein: String
) {
    fun toDomainModel() = RecipesDomainModel(
        id = this.id,
        title = this.title,
        calories = this.calories,
        carbs = this.carbs,
        fat = this.fat,
        image = this.image,
        protein = this.protein
    )
}
