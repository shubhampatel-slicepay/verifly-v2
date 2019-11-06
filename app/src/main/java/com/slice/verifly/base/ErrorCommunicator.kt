package com.slice.verifly.base

interface ErrorCommunicator {

    fun notifyOnError(errorMessage: String, nullify: Boolean? = true)
}