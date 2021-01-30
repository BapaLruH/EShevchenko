package ru.example.memesapp.data.datasources

import ru.example.memesapp.Component
import ru.example.memesapp.data.models.OperationResult
import ru.example.memesapp.data.models.RawData

class RemoteDataSource(private val memesApi: Component.MemesApi) : IDataSource<List<RawData>> {
    override suspend fun loadData(page: Int, category: String): OperationResult<List<RawData>> {
        return try {
            val responseData = memesApi.getMemes(category, page)
            OperationResult.Success(responseData.result)
        } catch (e: Exception) {
            OperationResult.Error(e)
        }
    }
}