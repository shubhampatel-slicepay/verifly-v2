package com.slice.verifly.data.local.pref

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    companion object {
        private val TAG = "AppPreferences"
        private const val BACKED_UP_PREF = "Verifly.BACKED_UP_PREF"
        private const val NON_BACKED_UP_PREF = "Verifly.NON_BACKED_UP_PREF"
        const val NON_BACKED_UP_EDITOR_TYPE = "non_backed_up"
        const val BACKED_UP_EDITOR_TYPE = "backed_up"
    }

    private val backedUpPref: SharedPreferences = context.getSharedPreferences(
        BACKED_UP_PREF,
        Context.MODE_PRIVATE
    )
    private val nonBackedUpPref: SharedPreferences = context.getSharedPreferences(
        NON_BACKED_UP_PREF,
        Context.MODE_PRIVATE
    )

    private val backedUpPrefEditor: SharedPreferences.Editor = backedUpPref.edit()
    private val nonBackedUpPrefEditor: SharedPreferences.Editor = nonBackedUpPref.edit()

    // Setter
    fun set(key: String, value: Any, type: String? = NON_BACKED_UP_EDITOR_TYPE) {
        var editor: SharedPreferences.Editor? = null
        when (type) {
            NON_BACKED_UP_EDITOR_TYPE -> editor = nonBackedUpPrefEditor
            BACKED_UP_EDITOR_TYPE -> editor = backedUpPrefEditor
        }
        editor?.let {
            when (value) {
                is Boolean -> {
                    editor.putBoolean(key, value)
                }
                is Int -> {
                    editor.putInt(key, value)
                }
                is Long -> {
                    editor.putLong(key, value)
                }
                is Float -> {
                    editor.putFloat(key, value)
                }
                is Double -> {
                    editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
                }
                is String -> {
                    editor.putString(key, value)
                }
                else -> {

                }
            }
            return@let it.commit()
        }
    }

    // Getters
    fun get(key: String, defaultValue: Boolean) : Boolean {
        return when {
            backedUpPref.contains(key) -> {
                backedUpPref.getBoolean(key, defaultValue)
            }
            nonBackedUpPref.contains(key) -> {
                nonBackedUpPref.getBoolean(key, defaultValue)
            }
            else -> defaultValue
        }
    }

    fun get(key: String, defaultValue: Int) : Int {
        return when {
            backedUpPref.contains(key) -> {
                backedUpPref.getInt(key, defaultValue)
            }
            nonBackedUpPref.contains(key) -> {
                nonBackedUpPref.getInt(key, defaultValue)
            }
            else -> defaultValue
        }
    }

    fun get(key: String, defaultValue: Long) : Long {
        return when {
            backedUpPref.contains(key) -> {
                backedUpPref.getLong(key, defaultValue)
            }
            nonBackedUpPref.contains(key) -> {
                nonBackedUpPref.getLong(key, defaultValue)
            }
            else -> defaultValue
        }
    }

    fun get(key: String, defaultValue: Float) : Float {
        return when {
            backedUpPref.contains(key) -> {
                backedUpPref.getFloat(key, defaultValue)
            }
            nonBackedUpPref.contains(key) -> {
                nonBackedUpPref.getFloat(key, defaultValue)
            }
            else -> defaultValue
        }
    }

    fun get(key: String, defaultValue: Double) : Double {
        return when {
            backedUpPref.contains(key) -> {
                java.lang.Double.longBitsToDouble(backedUpPref.getLong(key, defaultValue.toLong()))
            }
            nonBackedUpPref.contains(key) -> {
                java.lang.Double.longBitsToDouble(nonBackedUpPref.getLong(key, defaultValue.toLong()))
            }
            else -> defaultValue
        }
    }

    fun get(key: String, defaultValue: String) : String {
        return when {
            backedUpPref.contains(key) -> {
                backedUpPref.getString(key, defaultValue)
            }
            nonBackedUpPref.contains(key) -> {
                nonBackedUpPref.getString(key, defaultValue)
            }
            else -> defaultValue
        } ?: ""
    }

    // Utilities
    fun clearPreferences() {
        nonBackedUpPrefEditor.clear().commit()
    }
}