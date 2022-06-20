package com.fitness.presentation.food.list.model

import com.fitness.databinding.MealItemBinding
import com.fitness.presentation.utils.recycler.model.Id
import com.squareup.picasso.Picasso

data class MealUIModel(
    override val id: Long,
    val title: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val image: String,
    val protein: String,
    val onClick: () -> Unit
) : Id<Long> {
    infix fun onBind(binding: MealItemBinding) {
        binding.root.setOnClickListener { onClick.invoke() }
        Picasso.get().load(image).into(binding.foodImage)
        binding.foodName.text = title
    }
}
