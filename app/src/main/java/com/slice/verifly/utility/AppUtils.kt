package com.slice.verifly.utility

import android.os.Build
import android.text.TextUtils
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
}