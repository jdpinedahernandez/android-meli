package com.juanpineda.meli

import android.app.Application
import com.juanpineda.meli.di.DaggerMeliComponent
import com.juanpineda.meli.di.MeliComponent

open class MeliApp : Application() {

    lateinit var component: MeliComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component = initMeliComponent()
    }

    open fun initMeliComponent() = DaggerMeliComponent.factory().create(this)
}