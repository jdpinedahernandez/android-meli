package com.juanpineda.meli.di

import android.app.Application
import androidx.room.Room
import com.juanpineda.data.source.*
import com.juanpineda.meli.R
import com.juanpineda.data.database.ProductDatabase
import com.juanpineda.data.database.RoomDataSource
import com.juanpineda.data.server.MeliDb
import com.juanpineda.data.server.MeliDbDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    fun databaseProvider2(app: Application) = Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            "product-db"
    ).build()

    @Provides
    fun localDataSourceProvider2(db: ProductDatabase): LocalDataSource =
        RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider2(meliDB: MeliDb): RemoteDataSource =
        MeliDbDataSource(meliDB)
}