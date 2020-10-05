package com.juanpineda.meli

import android.app.Application
import com.juanpineda.data.database.ProductDatabase
import com.juanpineda.data.database.RoomDataSource
import com.juanpineda.data.repository.ProductsRepository
import com.juanpineda.data.server.MeliDb
import com.juanpineda.data.server.MeliDbDataSource
import com.juanpineda.data.source.LocalDataSource
import com.juanpineda.data.source.RemoteDataSource
import com.juanpineda.meli.ui.detail.view.DetailActivity
import com.juanpineda.meli.ui.detail.viewmodel.DetailViewModel
import com.juanpineda.meli.ui.main.view.MainActivity
import com.juanpineda.meli.ui.main.viewmodel.MainViewModel
import com.juanpineda.usecases.FindProductById
import com.juanpineda.usecases.GetPredictiveCategory
import com.juanpineda.usecases.GetProducts
import com.juanpineda.usecases.ToggleProductFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { ProductDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { MeliDbDataSource(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.mercadolibre.com/" }
    single { MeliDb(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { ProductsRepository(get(), get()) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetProducts(get()) }
        scoped { GetPredictiveCategory(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (id: String) -> DetailViewModel(id, get(), get()) }
        scoped { FindProductById(get()) }
        scoped { ToggleProductFavorite(get()) }
    }
}