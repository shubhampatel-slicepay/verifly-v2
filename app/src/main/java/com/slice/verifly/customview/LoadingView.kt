package com.slice.verifly.customview

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.slice.verifly.R
import com.slice.verifly.utility.inflateImage
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingView(val context: Context) {

    private var dialog: Dialog? = null

    init {
        dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            setContentView(R.layout.dialog_loading)
        }
        dialog?.iv_loading?.inflateImage(R.drawable.gif_loading)
    }

    fun showLoading() {
        dialog?.show()
    }

    fun hideLoading() {
        dialog?.dismiss()
    }
}