package com.fitness.presentation.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
fun EditText.textChanges(debounceTimeOut: Long = 1000) = callbackFlow {
    val watcher = addTextChangedListener {
        trySend(it ?: "")
    }
    awaitClose { removeTextChangedListener(watcher) }
}.debounce(debounceTimeOut)
