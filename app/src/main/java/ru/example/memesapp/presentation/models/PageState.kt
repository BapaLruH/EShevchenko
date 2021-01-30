package ru.example.memesapp.presentation.models

sealed class PageState<out T> {
    object Empty : PageState<Nothing>()
    object Loading : PageState<Nothing>()
    data class Success<out T : Any>(val data: T) : PageState<T>()
    data class Error(val errorMessage: String) : PageState<Nothing>()
}
