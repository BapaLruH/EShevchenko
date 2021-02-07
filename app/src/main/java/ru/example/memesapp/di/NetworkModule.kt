package ru.example.memesapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.example.memesapp.BuildConfig
import ru.example.memesapp.data.services.MemesApi
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class BaseUrl

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideJsonFactory(json: Json): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @BaseUrl
    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
            .addConverterFactory(converterFactory).build()
    }

    @Provides
    @Singleton
    fun provideMemesService(retrofit: Retrofit): MemesApi {
        return retrofit.create(MemesApi::class.java)
    }
}