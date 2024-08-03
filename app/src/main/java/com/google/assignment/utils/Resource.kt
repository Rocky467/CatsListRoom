package com.google.assignment.utils

sealed class Resource<T>(
    val data: T? = null,
    val error: String? = null,
) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorMessage: String) : Resource<T>(error = errorMessage)
}
