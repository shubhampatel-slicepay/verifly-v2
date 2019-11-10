package com.slice.verifly.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntRange
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.slice.verifly.R

private const val GLOBAL_TAG = "Extensions"

fun Any?.isObjectNotEmpty() : Boolean {
    this?.let {
        return true
    } ?: return false
}

fun String?.isStringNotEmpty() : Boolean {
    this?.let {
        return it.trim().isNotEmpty()
    } ?: return false
}

fun Activity.ifHasPermission(vararg permissions: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        permissions.all { singlePermission ->
            applicationContext.checkSelfPermission(singlePermission) == PackageManager.PERMISSION_GRANTED
        }
    }
    else true
}

fun Activity.askPermission(vararg permissions: String, @IntRange(from = 0) requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

fun Fragment.ifHasPermission(vararg permissions: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        permissions.all { singlePermission ->
            activity?.checkSelfPermission(singlePermission) == PackageManager.PERMISSION_GRANTED
        }
    }
    else true
}

fun Fragment.askPermission(vararg permissions: String, @IntRange(from = 0) requestCode: Int) {
    requestPermissions(permissions, requestCode)
}

val Context.isNetworkConnected: Boolean
    get() {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected == true
    }

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.snack(message: String): Snackbar {
    return Snackbar.make(this, message, Snackbar.LENGTH_SHORT).apply {
        setActionTextColor(Color.parseColor("#FF8500"))
        view.setBackgroundColor(Color.parseColor("#000000"))
        (view.findViewById(R.id.snackbar_text) as TextView).setTextColor(Color.parseColor("#FFFFFF"))
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        return@inTransaction if (addToStack) {
            add(frameId, fragment, fragment.javaClass.simpleName).addToBackStack(fragment.javaClass.simpleName)
        } else {
            add(frameId, fragment, fragment.javaClass.simpleName)
        }
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToStack: Boolean = true) {
    supportFragmentManager.inTransaction {
        return@inTransaction if (addToStack) {
            replace(frameId, fragment, fragment.javaClass.simpleName).addToBackStack(fragment.javaClass.simpleName)
        } else {
            replace(frameId, fragment, fragment.javaClass.simpleName)
        }
    }
}

fun Fragment.addFragment(fragment: Fragment, frameId: Int, addToStack: Boolean = true) {
    childFragmentManager.inTransaction {
        return@inTransaction if (addToStack) {
            add(frameId, fragment, fragment.javaClass.simpleName).addToBackStack(fragment.javaClass.simpleName)
        } else {
            add(frameId, fragment, fragment.javaClass.simpleName)
        }
    }
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, addToStack: Boolean = true) {
    childFragmentManager.inTransaction {
        return@inTransaction if (addToStack) {
            add(frameId, fragment, fragment.javaClass.simpleName).addToBackStack(fragment.javaClass.simpleName)
        } else {
            add(frameId, fragment, fragment.javaClass.simpleName)
        }
    }
}

fun Activity.disableScreen() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.enableScreen() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.blockScreenForTask(task: () -> Unit) {
    disableScreen()
    task.invoke()
    enableScreen()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Context.showAlertDialog(
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
): AlertDialog {
    val builder = AlertDialog.Builder(this)
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
}

fun ImageView?.inflateImage(uri: Uri, showProgress: Boolean = true) {
    this?.context?.let {
        if (showProgress) {
            val circularProgressDrawable = CircularProgressDrawable(it).apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }
            Glide.with(it)
                .load(uri)
                .centerCrop()
                .placeholder(circularProgressDrawable)
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        circularProgressDrawable.setVisible(false, false)
                        SlicePayLog.info(GLOBAL_TAG, "Glide -> uri = $uri -> exception = ${e?.message}")
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
                .into(this)
        } else {
            Glide.with(it)
                .load(uri)
                .centerCrop()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(this)
        }
    }
}

fun ImageView?.inflateImage(drawable: Int) {
    this?.context?.let {
        val circularProgressDrawable = CircularProgressDrawable(it).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        Glide.with(it)
            .load(drawable)
            .centerCrop()
            .transition(withCrossFade())
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
                    SlicePayLog.info(GLOBAL_TAG, "Glide -> exception = ${e?.message}")
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
            .into(this)
    }
}
