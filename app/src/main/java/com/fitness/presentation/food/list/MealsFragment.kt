package com.fitness.presentation.food.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitness.R
import com.fitness.databinding.FragmentMealsBinding
import com.fitness.presentation.common.BaseFragment
import com.fitness.presentation.utils.extensions.collectSharedFlowStarted
import com.fitness.presentation.utils.extensions.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MealsFragment : BaseFragment<MealsViewModel>(R.layout.fragment_meals) {
    override val viewModelClass = MealsViewModel::class

    private val adapter = MealsRecyclerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMealsBinding.bind(view).onViewBind()
    }

    override fun collectFlows() {
        super.collectFlows()
        collectSharedFlowStarted(viewModel.meals, adapter::submitList)
    }

    private fun FragmentMealsBinding.onViewBind() {
        meals.layoutManager = LinearLayoutManager(context)
        meals.adapter = adapter
        lifecycleScope.launchWhenCreated {
            search.textChanges().collect(viewModel::search)
        }
    }
}
