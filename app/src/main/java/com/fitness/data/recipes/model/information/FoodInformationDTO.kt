package com.fitness.data.recipes.model.information

import com.fitness.domain.recipes.model.information.FoodInformationDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class FoodInformationDTO(
    val veryHealthy: Boolean,
    val preparationMinutes: Int,
    val title: String,
    val image: String,
    val summary: String,
    val dishTypes: List<String>,
    val diets: List<String>
) {
    fun toDomainModel() = FoodInformationDomainModel(
        veryHealthy = this.veryHealthy,
        preparationMinutes = this.preparationMinutes,
        title = this.title,
        image = this.image,
        summary = this.summary,
        dishTypes = this.dishTypes,
        diets = this.diets
    )
}
