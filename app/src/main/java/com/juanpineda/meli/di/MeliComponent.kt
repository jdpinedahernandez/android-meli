package com.juanpineda.meli.di

import android.app.Application
import com.juanpineda.data.di.DataModule
import com.juanpineda.data.di.ServerModule
import com.juanpineda.meli.ui.detail.DetailActivityComponent
import com.juanpineda.meli.ui.detail.DetailActivityModule
import com.juanpineda.meli.ui.main.MainActivityComponent
import com.juanpineda.meli.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ServerModule::class])
interface MeliComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule): DetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MeliComponent
    }
}