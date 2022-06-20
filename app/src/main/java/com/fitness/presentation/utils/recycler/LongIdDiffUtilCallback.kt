package com.fitness.presentation.utils.recycler

import androidx.recyclerview.widget.DiffUtil
import com.fitness.presentation.utils.recycler.model.Id

class LongIdDiffUtilCallback<ID : Id<Long>> : DiffUtil.ItemCallback<ID>() {
    override fun areItemsTheSame(oldItem: ID, newItem: ID) = oldItem == newItem
    override fun areContentsTheSame(oldItem: ID, newItem: ID) = oldItem.id == newItem.id
}
