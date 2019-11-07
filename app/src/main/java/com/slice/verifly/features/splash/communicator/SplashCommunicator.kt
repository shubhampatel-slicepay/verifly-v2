package com.slice.verifly.features.splash.communicator

interface SplashCommunicator {

    fun onSplashAnimationFinished(status: Boolean)

    fun dismissActivity()
}