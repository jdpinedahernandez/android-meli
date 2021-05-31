package com.juanpineda.meli.di

import android.app.Application
import com.juanpineda.data.di.DataModule
import com.juanpineda.data.di.ServerModule
import com.juanpineda.meli.ui.modules.home.productdetail.ProductDetailFragmentComponent
import com.juanpineda.meli.ui.modules.home.productdetail.ProductDetailFragmentModule
import com.juanpineda.meli.ui.modules.home.products.ProductsFragmentComponent
import com.juanpineda.meli.ui.modules.home.products.ProductsFragmentModule
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentComponent
import com.juanpineda.meli.ui.modules.home.search.SearchFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ServerModule::class])
interface MeliComponent {

    fun plus(module: ProductDetailFragmentModule): ProductDetailFragmentComponent
    fun plus(module: SearchFragmentModule): SearchFragmentComponent
    fun plus(module: ProductsFragmentModule): ProductsFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MeliComponent
    }
}