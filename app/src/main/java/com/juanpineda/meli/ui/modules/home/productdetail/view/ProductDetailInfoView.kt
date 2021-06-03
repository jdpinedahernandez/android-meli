package com.juanpineda.meli.ui.modules.home.productdetail.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.juanpineda.domain.Product
import com.juanpineda.meli.R
import com.juanpineda.meli.ui.modules.home.common.formatAmount

class ProductDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setProduct(product: Product) = with(product) {
        text = buildSpannedString {

            bold { append(context.getString(R.string.product_detail_price)) }
            appendLine(formatAmount(price))

            bold { append(context.getString(R.string.product_detail_currency)) }
            appendLine(currencyId)

            bold { append(context.getString(R.string.product_detail_available_quantity)) }
            appendLine(availableQuantity.toString())

            bold { append(context.getString(R.string.product_detail_pictures)) }
            appendLine(pictures.size.toString())

            bold { append(warranty) }
        }
    }
}