package com.juanpineda.meli.ui.modules.home.productdetail.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.juanpineda.meli.R
import java.text.NumberFormat
import java.util.*

@BindingAdapter("loadUrl")
fun ImageView.loadUrl(url: String?) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("loadAmount")
fun TextView.loadAmount(amount: Number) {
    text = context.resources.getString(
        R.string.money,
        NumberFormat.getIntegerInstance(Locale.US).format(amount)
    )
}