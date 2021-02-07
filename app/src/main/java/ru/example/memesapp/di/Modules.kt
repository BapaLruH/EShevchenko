package ru.example.memesapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.example.memesapp.R
import ru.example.memesapp.data.datasources.IDataSource
import ru.example.memesapp.data.datasources.RemoteDataSource
import ru.example.memesapp.data.models.RawData
import ru.example.memesapp.data.repository.RemoteRepository
import ru.example.memesapp.presentation.repository.IRepository
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindRemoteDataSource(impl: RemoteDataSource): IDataSource<List<RawData>>
}

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRemoteRepository(impl: RemoteRepository): IRepository<List<RawData>>
}

@Qualifier
annotation class TabTitles

@Qualifier
annotation class TabCategories

@InstallIn(SingletonComponent::class)
@Module
class ViewPagerModule {
    @TabTitles
    @Singleton
    @Provides
    fun provideTabTitles(@ApplicationContext context: Context): List<String> {
        return listOf(
            context.getString(R.string.latest_page_title),
            context.getString(R.string.best_page_title),
            context.getString(R.string.hot_page_title)
        )
    }
    @TabCategories
    @Singleton
    @Provides
    fun provideTabCategory(@ApplicationContext context: Context): List<String> {
        return listOf(
            context.getString(R.string.latest_page_category),
            context.getString(R.string.best_page_category),
            context.getString(R.string.hot_page_category)
        )
    }

}