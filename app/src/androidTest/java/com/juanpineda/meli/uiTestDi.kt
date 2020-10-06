package com.juanpineda.meli

import android.app.Application
import com.juanpineda.data.di.DataModule
import com.juanpineda.data.server.MeliDb
import com.juanpineda.meli.di.AppModule
import com.juanpineda.meli.di.MeliComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, TestServerModule::class])
interface UiTestComponent: MeliComponent {

    val meliDB: MeliDb
    val mockWebServer: MockWebServer

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): UiTestComponent
    }
}

@Module
class TestServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider() = "http://127.0.0.1:8080"

    @Provides
    @Singleton
    fun mockWebServerProvider(): MockWebServer {
        var mockWebServer:MockWebServer? = null
        val thread = Thread(Runnable {
            mockWebServer = MockWebServer()
            mockWebServer?.start(8080)
        })
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Singleton
    fun lolServiceManagerProvider(
            @Named("baseUrl") baseUrl: String
    ): MeliDb
            = MeliDb(baseUrl)

}
