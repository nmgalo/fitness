package com.fitness.presentation.common

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import com.fitness.presentation.main.MainViewModel
import kotlin.reflect.KClass

abstract class BaseFragment<ViewModel : BaseViewModel>(@LayoutRes layout: Int) : Fragment(layout) {

    abstract val viewModelClass: KClass<ViewModel>

    protected val viewModel: ViewModel by lazy {
        createViewModelLazy(viewModelClass, { viewModelStore }).value
    }
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setHandler(parentViewModel)
        collectFlows()
    }

    open fun collectFlows() {}


    override fun onDestroy() {
        viewModel.removeHandler()
        super.onDestroy()
    }
}
