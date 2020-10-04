package com.juanpineda.meli.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.juanpineda.domain.Product
import com.juanpineda.meli.R
import com.juanpineda.meli.ui.common.formatAmount

class ProductDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setProduct(product: Product) = with(product) {
        text = buildSpannedString {

            bold { append(context.getString(R.string.product_detail_price)) }
            appendln(formatAmount(price))

            bold { append(context.getString(R.string.product_detail_currency)) }
            appendln(currencyId)

            bold { append(context.getString(R.string.product_detail_available_quantity)) }
            appendln(availableQuantity.toString())

            bold { append(context.getString(R.string.product_detail_favorite)) }
            appendln(
                if (favorite) context.getString(R.string.product_detail_favorite_yes)
                else context.getString(R.string.product_detail_favorite_no)
            )

            bold { append(context.getString(R.string.product_detail_pictures)) }
            appendln(pictures.size.toString())

            bold { append(warranty) }
        }
    }
}