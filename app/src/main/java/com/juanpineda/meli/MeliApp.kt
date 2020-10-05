package com.juanpineda.meli

import android.app.Application

open class MeliApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}