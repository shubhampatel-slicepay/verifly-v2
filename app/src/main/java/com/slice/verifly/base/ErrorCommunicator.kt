package com.slice.verifly.base

interface ErrorCommunicator {

    fun notifyError(errorMessage: String, nullify: Boolean? = true)
}