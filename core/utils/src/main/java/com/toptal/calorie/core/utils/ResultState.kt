package com.toptal.calorie.core.utils

sealed class ResultState<out T : Any> {
    data class Progress(val isLoading: Boolean) : ResultState<Nothing>()
    data class Success<out T : Any>(val data: T? = null) : ResultState<T>()
    data class Error(
        val exception: Throwable? = null,
        val message: String = exception?.message ?: "An unknown error occurred!"
    ) : ResultState<Nothing>()
}