package ru.example.memesapp

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import ru.example.memesapp.data.datasources.RemoteDataSource
import ru.example.memesapp.data.models.ResponseData
import ru.example.memesapp.data.repository.RemoteRepository
import java.util.concurrent.TimeUnit

class Component {
    private val dataSource: RemoteDataSource = RemoteDataSource(RetrofitModule.memesApi)
    val repository: RemoteRepository

    init {
        repository = RemoteRepository(dataSource)
    }

    object RetrofitModule {
        private val json = Json {
            ignoreUnknownKeys = true
        }

        private val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        @Suppress("EXPERIMENTAL_API_USAGE")
        private val retrofit = Retrofit.Builder().client(client).baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val memesApi = retrofit.create(MemesApi::class.java)
    }

    interface MemesApi {
        @GET("{category}/{page}?json=true")
        suspend fun getMemes(
            @Path("category") category: String,
            @Path("page") page: Int
        ): ResponseData
    }
}