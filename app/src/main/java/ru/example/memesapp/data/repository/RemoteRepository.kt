package ru.example.memesapp.data.repository

import ru.example.memesapp.data.datasources.IDataSource
import ru.example.memesapp.data.models.OperationResult
import ru.example.memesapp.data.models.RawData
import ru.example.memesapp.presentation.repository.IRepository

class RemoteRepository(private val dataSource: IDataSource<List<RawData>>) : IRepository<List<RawData>> {
    override suspend fun loadData(page: Int, category: String): OperationResult<List<RawData>> =
        dataSource.loadData(page, category)
}