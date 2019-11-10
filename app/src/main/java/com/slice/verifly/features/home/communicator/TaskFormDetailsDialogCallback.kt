package com.slice.verifly.features.home.communicator

interface TaskFormDetailsDialogCallback {

    fun onClosed()

    fun onSoftBackButtonPressed(closeDialog: Boolean = true)
}