package com.fitness.presentation.food.list

import com.fitness.domain.recipes.SearchRecipesUseCase
import com.fitness.domain.recipes.model.RecipesDomainModel
import com.fitness.presentation.common.BaseViewModel
import com.fitness.presentation.food.list.model.MealUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val useCase: SearchRecipesUseCase
) : BaseViewModel() {

    private val _meals = MutableSharedFlow<List<MealUIModel>>()
    val meals = _meals.asSharedFlow()

    fun search(query: String) {
        execute(withLoader = true) {
            _meals.emit(useCase.execute(query).map { it.toUIModel() })
        }
    }

    private fun RecipesDomainModel.toUIModel() = MealUIModel(
        id = this.id,
        title = this.title,
        calories = this.calories,
        carbs = this.carbs,
        fat = this.fat,
        image = this.image,
        protein = this.protein,
    ) {
        // TODO handle item click
    }

}

