package com.juanpineda.meli

class TestApplication: MeliApp() {

    override fun initMeliComponent() = DaggerUiTestComponent.factory().create(this)

}