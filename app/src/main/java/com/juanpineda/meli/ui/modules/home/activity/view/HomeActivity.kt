package com.juanpineda.meli.ui.modules.home.activity.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juanpineda.meli.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)
    }
}