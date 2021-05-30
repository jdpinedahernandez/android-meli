package com.juanpineda.meli.ui.modules.home.search.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.juanpineda.meli.R
import com.juanpineda.meli.databinding.DialogSearchBinding

class SearchDialogFragment : BottomSheetDialogFragment() {
    class Builder {
        private var setOnFavoriteClickListener: (() -> Unit)? = null
        private var setOnViewedProductsClickListener: (() -> Unit)? = null
        private val arguments = Bundle()

        fun setOnFavoriteClickListener(listener: (() -> Unit)? = null) = apply {
            setOnFavoriteClickListener = listener
        }

        fun setOnViewedProductsClickListener(listener: (() -> Unit)? = null) = apply {
            setOnViewedProductsClickListener = listener
        }

        fun create(): SearchDialogFragment {
            val dialog = SearchDialogFragment()
            dialog.arguments = arguments
            dialog.setOnFavoriteClickListener = setOnFavoriteClickListener
            dialog.setOnViewedProductsClickListener = setOnViewedProductsClickListener
            return dialog
        }
    }

    private var setOnFavoriteClickListener: (() -> Unit)? = null
    private var setOnViewedProductsClickListener: (() -> Unit)? = null
    lateinit var binding: DialogSearchBinding

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet = it.findViewById<FrameLayout>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = WRAP_CONTENT
        }
        view?.post {
            val parent = view?.parent as View
            val params = parent.layoutParams as LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.peekHeight = view?.measuredHeight ?: 0
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_search,
            container,
            false
        )
        binding.textViewFavoriteDialog.setOnClickListener {
            setOnFavoriteClickListener?.invoke()
            dismiss()
        }
        binding.textViewViewedDialog.setOnClickListener {
            setOnViewedProductsClickListener?.invoke()
            dismiss()
        }
        binding.textViewCloseDialog.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}