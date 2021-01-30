package ru.example.memesapp.presentation.repository

import ru.example.memesapp.data.models.OperationResult

interface IRepository<out T> {
    suspend fun loadData(page: Int, category: String): OperationResult<T>
}