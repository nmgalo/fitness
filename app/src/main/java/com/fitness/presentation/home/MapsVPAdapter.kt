package com.fitness.presentation.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fitness.R
import com.fitness.databinding.ItemPagerImageBinding
import com.fitness.presentation.home.model.LocationUIModel
import com.fitness.presentation.utils.extensions.inflate
import com.fitness.presentation.utils.recycler.LongIdDiffUtilCallback

class MapsVPAdapter :
    ListAdapter<LocationUIModel, MapsVPAdapter.ViewHolder>(LongIdDiffUtilCallback<LocationUIModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_pager_image))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position) onBind ItemPagerImageBinding.bind(holder.itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
