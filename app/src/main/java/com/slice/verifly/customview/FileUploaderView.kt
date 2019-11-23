package com.slice.verifly.customview

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.api.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.slice.verifly.R
import kotlinx.android.synthetic.main.custom_layout_file_uploader.view.*

class FileUploaderView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context, null, 0)

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_layout_file_uploader, this, false)
        setDefault()
    }

    private fun setDefault() {
        val image = ContextCompat.getDrawable(iv_attachment_img.context, R.drawable.file_upload)
        image?.let {
            iv_attachment_img.setImageDrawable(it)
        }
        tv_attachment_name.visibility = View.GONE
    }

    fun loadImage(uri: Uri) {
        iv_attachment_img?.load(uri) {
            crossfade(true)
            placeholder(R.drawable.gif_uploading)
            transformations(CircleCropTransformation())
            memoryCachePolicy(CachePolicy.DISABLED)
        }
    }

    fun showAttachmentName(name: String) {
        tv_attachment_name.visibility = View.VISIBLE
        tv_attachment_name.text = name
    }
}