package com.slice.verifly.customview

import android.app.Dialog
import android.content.Context
import android.view.Window
import coil.api.load
import com.slice.verifly.R
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
        dialog?.iv_loading?.load(R.drawable.gif_loading)
    }

    fun showLoading() {
        dialog?.show()
    }

    fun hideLoading() {
        dialog?.dismiss()
    }
}