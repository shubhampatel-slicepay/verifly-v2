package com.slice.verifly.data.repo

import com.slice.verifly.data.local.pref.AppPreferences
import com.slice.verifly.data.local.pref.PreferencesKeys
import com.slice.verifly.models.loginresponse.Data
import com.slice.verifly.models.response.ResponseModel
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject

sealed class Decisions: KoinComponent {

    object Manager: Decisions()

    private val preferences: AppPreferences by inject()
    private val dataManagerHelper: DataManagerHelper by inject()

    /**
     * Getters (exposed)
     */

    fun fetchXAccessToken(): String {
        return preferences.get(PreferencesKeys.ACCESS_TOKEN, defaultValue = "")
    }

    fun fetchUToken(): String {
        return preferences.get(PreferencesKeys.USER_TOKEN, defaultValue = "")
    }

    /**
     * Setters (un-exposed)
     */

    private fun setXAccessToken(xAccessToken: String?) {
        xAccessToken?.let {
            preferences.set(PreferencesKeys.ACCESS_TOKEN, xAccessToken)
        }
    }

    private fun setUToken(uToken: String?) {
        uToken?.let {
            preferences.set(PreferencesKeys.USER_TOKEN, uToken)
        }
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
    }

    /**
     * Dumps data in order to make decisions (exposed)
     */

    fun loginData(phoneNumber: String?, data: Data?) {
        data?.userToken?.let { setUToken(it) }
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