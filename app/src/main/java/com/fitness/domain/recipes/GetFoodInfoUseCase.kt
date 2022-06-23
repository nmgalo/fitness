package com.fitness.domain.recipes

import javax.inject.Inject

class GetFoodInfoUseCase @Inject constructor(
    private val recipesRepo: RecipesRepo
) {
    suspend operator fun invoke(foodId: Long) = recipesRepo.getInformationById(foodId)
}
