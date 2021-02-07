package ru.example.memesapp.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import ru.example.memesapp.data.models.ResponseData

interface MemesApi {
    @GET("{category}/{page}?json=true")
    suspend fun getMemes(
        @Path("category") category: String,
        @Path("page") page: Int
    ): ResponseData
}