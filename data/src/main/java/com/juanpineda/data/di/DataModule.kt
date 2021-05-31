package com.juanpineda.data.di

import com.juanpineda.data.repository.CategoriesRepository
import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun productsRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ) = ProductsRepository(localDataSource, remoteDataSource)

    @Provides
    fun categoriesRepositoryProvider(
        remoteDataSource: RemoteDataSource
    ) = CategoriesRepository(remoteDataSource)
}