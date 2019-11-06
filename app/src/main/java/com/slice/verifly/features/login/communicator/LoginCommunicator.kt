package com.slice.verifly.features.login.communicator

interface LoginCommunicator {

    // LoginFragment
    fun showResetPasswordScreen()

    // LoginFragment
    fun onSuccessfullyLoggedIn()

    // ChangePasswordFragment
    fun onPasswordChanged()
}