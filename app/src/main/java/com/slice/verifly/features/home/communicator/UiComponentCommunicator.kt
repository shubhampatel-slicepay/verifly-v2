package com.slice.verifly.features.home.communicator

interface UiComponentCommunicator {

    fun onMediaAccessRequested(reqCode: Int)

    fun uploadFilesToCloudinary(files: Array<String?>)
}