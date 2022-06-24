package com.fitness.presentation.home.model

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.fitness.databinding.ItemPagerImageBinding
import com.fitness.presentation.utils.extensions.convertLongToTime
import com.fitness.presentation.utils.recycler.model.Id


data class LocationUIModel(
    override val id: Long,
    val image: Bitmap?,
    var timestamp: Long = 0L,
    var avgSpeedInKMH: Float = 0f,
    var distanceInMeters: Int = 0,
    var timeInMillis: Long = 0L,
    var caloriesBurned: Int = 0
) : Id<Long> {
    @SuppressLint("SetTextI18n")
    infix fun onBind(binding: ItemPagerImageBinding) {
        binding.ivMap.setImageBitmap(image)
        binding.tvDistance.text = "$distanceInMeters Meters"
        binding.tvCalories.text = "$caloriesBurned Cal"
        binding.tvTime.text = timeInMillis.convertLongToTime()
        binding.tvAvgSpeed.text = "$avgSpeedInKMH km/h"
    }
}
