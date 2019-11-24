package com.slice.verifly.utility

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
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
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object AppUtils {

    private const val TAG = "AppUtils"

    fun isObjectNotEmpty(obj: Any?) : Boolean {
        obj?.let {
            return true
        } ?: return false
    }

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

    fun inflateImage(imageView: ImageView?, uri: Uri?, drawable: Int?, showProgress: Boolean = true) {
        imageView?.let {
            if (showProgress) {
                val circularProgressDrawable = CircularProgressDrawable(it.context).apply {
                    strokeWidth = 5f
                    centerRadius = 30f
                    start()
                }
                uri?.let { value ->
                    Glide.with(it.context)
                        .load(value)
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
                }
                drawable?.let { value ->
                    Glide.with(it.context)
                        .load(value)
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

                }
            } else {
                uri?.let { value ->
                    Glide.with(it)
                        .load(value)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(it)
                }
                drawable?.let { value ->
                    Glide.with(it)
                        .load(value)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .into(it)
                }
            }
        }
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(date)
    }

    fun openSettings(context: Context?) {
        context?.let {
            val uri = Uri.fromParts("package", it.packageName, null)
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .setData(uri)
            it.startActivity(intent)
        }
    }

    fun createIntentChooser(target: Intent, title: String, extraIntents: Array<Intent>): Intent {
        return Intent.createChooser(target, title).putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents)
    }

    fun dispatchGalleryIntent(
        imagesRequired: Boolean = true,
        videosRequired: Boolean = false,
        pdfsRequired: Boolean = false,
        multiplesRequired: Boolean = false,
        uri: Uri? = null
    ): Intent? {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT, uri)
            .addCategory(Intent.CATEGORY_OPENABLE)
        var mimeTypes: Array<String>? = null
        when {
            imagesRequired and videosRequired and pdfsRequired -> {
                galleryIntent.type = "*/*"
                mimeTypes = arrayOf("image/*", "video/*", "application/pdf")
            }
            imagesRequired and videosRequired and !pdfsRequired -> {
                galleryIntent.type = "*/*"
                mimeTypes = arrayOf("image/*", "video/*")
            }
            imagesRequired and !videosRequired and pdfsRequired -> {
                galleryIntent.type = "*/*"
                mimeTypes = arrayOf("image/*", "application/pdf")
            }
            !imagesRequired and videosRequired and pdfsRequired -> {
                galleryIntent.type = "*/*"
                mimeTypes = arrayOf("video/*", "application/pdf")
            }
            imagesRequired and !videosRequired and !pdfsRequired -> galleryIntent.type = "image/*"
            !imagesRequired and videosRequired and !pdfsRequired -> galleryIntent.type = "video/*"
            !imagesRequired and !videosRequired and pdfsRequired -> galleryIntent.type = "application/pdf"
        }
        mimeTypes?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, it)
            }
        }
        if (multiplesRequired) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            }
        }
        return galleryIntent
    }

    fun dispatchCameraIntent(context: Context?): Pair<Intent?, String?> {
        var cameraIntent: Intent? = null
        var currentFilePath: String? = null
        context?.let {
            cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent?.resolveActivity(context.packageManager)?.let {
                var photoFile: File? = null
                try {
                    photoFile = FileUtils.createImageFile(context)
                    currentFilePath = photoFile.absolutePath
                } catch (e: IOException) {
                    SlicePayLog.info(TAG, "dispatchCameraIntent: ${e.message}")
                }
                photoFile?.let {
                    val photoUri = FileUtils.getUriForFile(context, file = it)
                    cameraIntent?.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                }
            }
        }
        return Pair(cameraIntent, currentFilePath)
    }

    fun configureCloudinary(): HashMap<String, Any> {
        return HashMap<String, Any>().apply {
            put("cloud_name", "slice")
            put("phash", true)
            put("api_key", "327795729794347")
            put("api_secret", "v-7oGOFIYRy3Fd5kdBLUYpJ3VZA")
        }
    }
}