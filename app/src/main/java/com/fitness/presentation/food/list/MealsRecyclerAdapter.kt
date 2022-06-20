package com.fitness.presentation.food.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitness.R
import com.fitness.databinding.MealItemBinding
import com.fitness.presentation.food.list.model.MealUIModel
import com.fitness.presentation.utils.extensions.inflate
import com.fitness.presentation.utils.recycler.LongIdDiffUtilCallback

class MealsRecyclerAdapter :
    ListAdapter<MealUIModel, MealsRecyclerAdapter.ViewHolder>(LongIdDiffUtilCallback<MealUIModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.meal_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position) onBind MealItemBinding.bind(holder.itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
