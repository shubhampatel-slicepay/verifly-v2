package com.slice.verifly.utility

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.slice.verifly.R

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
