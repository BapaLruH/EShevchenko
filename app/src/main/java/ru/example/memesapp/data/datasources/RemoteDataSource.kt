package ru.example.memesapp.data.datasources

import ru.example.memesapp.data.models.OperationResult
import ru.example.memesapp.data.models.RawData
import ru.example.memesapp.data.services.MemesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val memesApi: MemesApi) :
    IDataSource<List<@JvmSuppressWildcards RawData>> {
    override suspend fun loadData(page: Int, category: String): OperationResult<List<RawData>> {
        return try {
            val responseData = memesApi.getMemes(category, page)
            OperationResult.Success(responseData.result)
        } catch (e: Exception) {
            OperationResult.Error(e)
        }
    }
}