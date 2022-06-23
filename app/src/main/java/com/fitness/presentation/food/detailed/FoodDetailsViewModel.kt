package com.fitness.presentation.food.detailed

import androidx.lifecycle.SavedStateHandle
import com.fitness.domain.recipes.GetFoodInfoUseCase
import com.fitness.domain.recipes.model.information.FoodInformationDomainModel
import com.fitness.presentation.common.BaseViewModel
import com.fitness.presentation.food.detailed.model.FoodInformationUIModel
import com.fitness.presentation.utils.extensions.getOrThrow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val getFoodInfoUseCase: GetFoodInfoUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _information = MutableSharedFlow<FoodInformationUIModel>()
    val information = _information.asSharedFlow()

    init {
        get(savedStateHandle.getOrThrow<Long>("recipeId"))
    }

    fun get(foodId: Long) {
        execute(withLoader = true) {
            _information.emit(getFoodInfoUseCase(foodId).toUIModel())
        }
    }

    private fun FoodInformationDomainModel.toUIModel() = FoodInformationUIModel(
        veryHealthy = this.veryHealthy,
        preparationMinutes = this.preparationMinutes,
        title = this.title,
        image = this.image,
        summary = this.summary,
        dishTypes = this.dishTypes,
        diets = this.diets
    )
}
