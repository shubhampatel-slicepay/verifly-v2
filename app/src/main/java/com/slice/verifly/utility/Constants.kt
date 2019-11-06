package com.slice.verifly.utility

object Constants {
    const val ANDROID = "android"

    // header params

    const val X_ACCESS_TOKEN_AUTH_HEADER = "x-access-token"
    const val I_ACCESS_TOKEN_AUTH_HEADER = "i-access-token"
    const val PLATFORM_AUTH_HEADER = "platform"

    // flags

    const val SUCCESS_RESPONSE_STATUS = "success"
    const val FAILURE_RESPONSE_STATUS = "fail"
    const val ERROR_RESPONSE_STATUS = "error"
    const val NEW_STATUS = "new"
    const val ONGOING_STATUS = "ongoing"
    const val COMPLETED_STATUS = "completed"

    // messages

    const val ERROR_TOAST_MESSAGE = "Something went wrong."
    const val RETRY_TOAST_MESSAGE = "Something went wrong. Please try again."
    const val NO_INTERNET_MESSAGE = "No internet connection."
    const val UPDATE_MESSAGE = "It seems you're using an old version. Please update to the new version."
    const val ERROR_FETCHING_DATA_MESSAGE = "Sorry! Unable to fetch data. Please try again later."

    // permission requests

    const val READ_PHONE_STATE_PER_REQ_CODE = 101

    // view requests

    // Parcel keys

    const val USER_TASK_SELECTED = "user-task"
}