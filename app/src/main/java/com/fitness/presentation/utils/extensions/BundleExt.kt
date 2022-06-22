package com.fitness.presentation.utils.extensions

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getOrThrow(key: String) = this.get<T>(key) ?: error("$key can't be null")
