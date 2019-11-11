package com.slice.verifly.customview

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.api.load
import com.slice.verifly.R
import kotlinx.android.synthetic.main.layout_file_uploader.view.*

class FileUploaderView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): RelativeLayout(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null)

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_file_uploader, this, false)
        setDefault()
    }

    private fun setDefault() {
        iv_attachment_img.visibility = View.VISIBLE
        pb_uploading.visibility = View.GONE
        val image = ContextCompat.getDrawable(iv_attachment_img.context, R.drawable.file_upload)
        image?.let {
            iv_attachment_img.setImageDrawable(it)
        }
    }

    private fun showProgress() {
        iv_attachment_img.visibility = View.VISIBLE
        iv_attachment_img.alpha = .5f
        pb_uploading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        pb_uploading.visibility = View.GONE
        iv_attachment_img.visibility = View.VISIBLE
        iv_attachment_img.alpha = 1f
    }

    fun isInProgress(): Boolean {
        return pb_uploading.isVisible
    }

    fun loadImage(uri: Uri) {
        showProgress()
        iv_attachment_img.load(uri)
        hideProgress()
    }
}