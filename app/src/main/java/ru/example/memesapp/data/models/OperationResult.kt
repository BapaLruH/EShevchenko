package ru.example.memesapp.data.models

sealed class OperationResult<out T> {
    data class Success<T: Any>(val data: T) : OperationResult<T>()
    data class Error(val error: Exception) : OperationResult<Nothing>()
}

