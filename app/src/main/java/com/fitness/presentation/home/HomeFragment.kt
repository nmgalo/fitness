package com.fitness.presentation.home

import com.fitness.R
import com.fitness.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    override val viewModelClass = HomeViewModel::class

}