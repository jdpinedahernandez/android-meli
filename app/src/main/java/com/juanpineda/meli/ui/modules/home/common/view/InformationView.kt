package com.juanpineda.meli.ui.modules.home.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.juanpineda.meli.R

class InformationView : ConstraintLayout {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private lateinit var textViewError: TextView
    private lateinit var buttonRetry: Button

    private fun init(context: Context) {
        inflate(context, R.layout.view_information, this)
        initComponents()
    }

    private fun initComponents() {
        textViewError = findViewById(R.id.text_view_error)
        buttonRetry = findViewById(R.id.button_retry)
    }

    fun showErrorView(onErrorListener: () -> Unit) =
        updateUI(
            errorTitle = R.string.product_main_error,
            icon = R.drawable.ic_error,
            buttonTitle = R.string.information_view_error_button,
            listener = onErrorListener
        )

    fun showEmptyView(onEmptyListener: () -> Unit) =
        updateUI(
            errorTitle = R.string.product_main_empty,
            icon = R.drawable.ic_empty,
            buttonTitle = R.string.information_view_empty_button,
            listener = onEmptyListener
        )

    private fun updateUI(
        @StringRes errorTitle: Int,
        @DrawableRes icon: Int,
        @StringRes buttonTitle: Int,
        listener: () -> Unit
    ) {
        textViewError.text = context.getText(errorTitle)
        textViewError.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            null,
            ContextCompat.getDrawable(context, icon)
        )
        buttonRetry.text = context.getText(buttonTitle)
        buttonRetry.setOnClickListener { listener.invoke() }
    }
}