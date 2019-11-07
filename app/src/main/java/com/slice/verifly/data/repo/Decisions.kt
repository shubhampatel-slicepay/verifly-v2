package com.slice.verifly.data.repo

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import com.slice.verifly.data.local.pref.AppPreferences
import com.slice.verifly.data.local.pref.PreferencesKeys
import com.slice.verifly.models.loginresponse.Data
import com.slice.verifly.models.response.ResponseModel
import com.slice.verifly.utility.SlicePayLog
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

sealed class Decisions: KoinComponent {

    object Manager: Decisions()

    private val TAG = "Decisions"
    private val preferences: AppPreferences by inject()
    private val dataManagerHelper: DataManagerHelper by inject()
    private val appContext: Context by inject()

    /**
     * Getters (exposed)
     */

    fun fetchXAccessToken(): String {
        return preferences.get(PreferencesKeys.ACCESS_TOKEN, defaultValue = "")
    }

    fun fetchUToken(): String {
        return preferences.get(PreferencesKeys.USER_TOKEN, defaultValue = "")
    }

    fun getUserLoggedInStatus(): Boolean {
        return preferences.get(PreferencesKeys.LOGGED_IN_STATUS, false)
    }

    /**
     * Setters (un-exposed)
     */

    private fun setXAccessToken(xAccessToken: String?) {
        xAccessToken?.let {
            preferences.set(PreferencesKeys.ACCESS_TOKEN, it)
        }
    }

    private fun setUToken(uToken: String?) {
        uToken?.let {
            preferences.set(PreferencesKeys.USER_TOKEN, it)
        }
    }

    fun setUserLoggedInStatus(status: Boolean) {
        preferences.set(PreferencesKeys.LOGGED_IN_STATUS, status)
    }

    /**
     * Service requests (exposed)
     */

    fun generateToken(): Boolean {
        val authResponse = runBlocking { dataManagerHelper.authenticate() }
        authData(authResponse?.body)
        authResponse?.body?.token?.let {
            return it.isNotEmpty()
        }
        return false
    }

    fun logOutUser(isTokenExpired: Boolean? = true) {
        preferences.clearPreferences()
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                val appManager = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                appManager.clearApplicationUserData() // note: it has a return value!
            } else {
                Runtime.getRuntime().exec("pm clear ${appContext.packageName}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            SlicePayLog.info(TAG, e.message)
        }
    }

    /**
     * Dumps data in order to make decisions (exposed)
     */

    fun loginData(phoneNumber: String?, userToken:String?, data: Data?) {
        userToken?.let {
            setUToken(it)
            setUserLoggedInStatus(true)
        }
        data?.userid?.let { preferences.set(PreferencesKeys.USER_ID, it) }
        data?.name?.let { preferences.set(PreferencesKeys.USER_NAME, it) }
        phoneNumber?.let { preferences.set(PreferencesKeys.USER_PHONE_NUMBER, it) }
        if (data?.status == true) {
            preferences.set(PreferencesKeys.LOGGED_IN_STATUS, true)
        }
    }

    fun authData(data: ResponseModel?) {
        data?.token?.let { setXAccessToken(it) }
        data?.expiresAt?.let { preferences.set(PreferencesKeys.TOKEN_EXPIRY, it) }
    }
}