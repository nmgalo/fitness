package com.fitness.presentation.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.R
import com.fitness.databinding.FragmentHomeBinding
import com.fitness.presentation.common.BaseFragment
import com.fitness.presentation.food.list.MealsRecyclerAdapter
import com.fitness.presentation.utils.extensions.collectLatestStateFlowStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    override val viewModelClass = HomeViewModel::class

    private val adapter = MapsVPAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentHomeBinding.bind(view).onViewBind()
    }

    private fun FragmentHomeBinding.onViewBind() {
        vpLoadedMaps.adapter = adapter
    }

    override fun collectFlows() {
        super.collectFlows()
        collectLatestStateFlowStarted(viewModel.locations){
            adapter.submitList(it)
        }
    }

}