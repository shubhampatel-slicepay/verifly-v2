package com.slice.verifly.utility

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    private const val TAG = "AppUtils"

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        if (model.startsWith(manufacturer)) {
            return capitalize(model)
        }
        return capitalize(manufacturer.plus(" ").plus(model))
    }

    private fun capitalize(str: String): String {
        if (TextUtils.isEmpty(str)) return str
        val strArr = str.toCharArray()
        var capitalizeNext = true
        val phrase = StringBuilder()
        for (c in strArr) {
            if (capitalizeNext and c.isLetter()) {
                phrase.append(c.toUpperCase())
                capitalizeNext = false
                continue
            } else {
                capitalizeNext = true
            }
            phrase.append(c)
        }
        return phrase.toString()
    }

    fun getCurrentTimeStamp(): String {
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun showAlertDialog(
        context: Context?,
        title: String?,
        message: String?,
        cancelable: Boolean = true,
        cancelableTouchOutside: Boolean = true,
        positiveButton: String? = null,
        positiveAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
        negativeButton: String? = null,
        negativeAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null,
        neutralButton: String? = null,
        neutralAction: ((dialog: DialogInterface, which: Int) -> Unit)? = null
    ): AlertDialog? {
        context?.let {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                title?.let { setTitle(it) }
                message?.let { setMessage(it) }
                setCancelable(cancelable)
                this.create().setCanceledOnTouchOutside(cancelableTouchOutside)
                positiveButton?.let { text ->
                    positiveAction?.let {
                        setPositiveButton(text, it)
                    } ?: setPositiveButton(text, null)
                } ?: setPositiveButton(null, null)
                negativeButton?.let { text ->
                    negativeAction?.let {
                        setNegativeButton(text, it)
                    } ?: setNegativeButton(text, null)
                } ?: setNegativeButton(null, null)
                neutralButton?.let { text ->
                    neutralAction?.let {
                        setNeutralButton(text, it)
                    } ?: setNeutralButton(text, null)
                } ?: setNeutralButton(null, null)
            }
            val alertDialog = builder.create()
            alertDialog.show()
            return alertDialog
        } ?: return null
    }

    fun inflateImage(imageView: ImageView?, uri: Uri, showProgress: Boolean = true) {
        imageView?.let {
            if (showProgress) {
                val circularProgressDrawable = CircularProgressDrawable(it.context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                }
                Glide.with(it.context)
                    .load(uri)
                    .centerCrop()
                    .placeholder(circularProgressDrawable)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            circularProgressDrawable.setVisible(false, false)
                            SlicePayLog.info(TAG, "Glide -> uri = $uri -> exception = ${e?.message}")
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            circularProgressDrawable.setVisible(false, false)
                            return false
                        }
                    })
                    .into(it)
            } else {
                Glide.with(it)
                    .load(uri)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(it)
            }
        }
    }

    fun inflateImage(imageView: ImageView?, drawable: Int) {
        imageView?.let {
            val circularProgressDrawable = CircularProgressDrawable(it.context).apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }
            Glide.with(it.context)
                .load(drawable)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(circularProgressDrawable)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circularProgressDrawable.setVisible(false, false)
                        SlicePayLog.info(TAG, "Glide -> exception = ${e?.message}")
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circularProgressDrawable.setVisible(false, false)
                        return false
                    }
                })
                .into(it)
        }
    }

}