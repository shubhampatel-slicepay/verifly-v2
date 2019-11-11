package com.slice.verifly.features.home.communicator

interface TaskFormDetailsDialogCallback {

    fun onDismissed()

    fun onSoftBackButtonPressed(dismiss: Boolean = true)
}