package com.juanpineda.data.di

import com.juanpineda.data.server.MeliDb
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider() = "https://api.mercadolibre.com/"

    @Singleton
    @Provides
    fun meliDBProvider(@Named("baseUrl") baseUrl: String) =
        MeliDb(baseUrl)
}