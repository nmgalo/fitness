package com.fitness.presentation.utils.extensions

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


fun <T> Fragment.collectLatestStateFlowCreated(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launchWhenCreated {
        flow.collectLatest(collect)
    }
}

fun <T> Fragment.collectLatestStateFlowStarted(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        flow.collectLatest(collect)
    }
}

fun <T> LifecycleService.collectLatestStateFlowStarted(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        flow.collectLatest(collect)
    }
}


fun <T> Fragment.collectSharedFlowCreated(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launchWhenCreated {
        flow.collect(collect)
    }
}

fun <T> Fragment.collectSharedFlowStarted(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        flow.collect(collect)
    }
}


