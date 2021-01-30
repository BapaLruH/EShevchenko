package ru.example.memesapp.data.datasources

import ru.example.memesapp.data.models.OperationResult

interface IDataSource<T> {
    suspend fun loadData(page: Int, category: String): OperationResult<T>
}