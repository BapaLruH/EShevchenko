package ru.example.memesapp.data.repository

import ru.example.memesapp.data.datasources.IDataSource
import ru.example.memesapp.data.models.OperationResult
import ru.example.memesapp.data.models.RawData
import ru.example.memesapp.presentation.repository.IRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val dataSource: @JvmSuppressWildcards IDataSource<List<RawData>>
) : IRepository<List<@JvmSuppressWildcards RawData>> {
    override suspend fun loadData(page: Int, category: String): OperationResult<List<RawData>> =
        dataSource.loadData(page, category)
}