package com.slice.verifly.base

import retrofit2.Response

class ApiResponse<T> {
    val body: T?
    val throwable: Throwable?

    var code: Int = 0
    var errorMessage: String? = null
    var isApiCallSuccessful: Boolean = true

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
        } else {
            body = null
            isApiCallSuccessful = false
            response.errorBody()?.let {
                errorMessage = it.string()
            }
            if (errorMessage.isNullOrEmpty()) {
                errorMessage = response.message()
            }
        }
        throwable = null
    }

    constructor(response: Throwable) {
        body = null
        throwable = response
        errorMessage = response.message
        isApiCallSuccessful = false
    }
}