package com.slice.verifly.features.login

interface LoginCommunicator {

    // LoginFragment
    fun showResetPasswordScreen()

    // LoginFragment
    fun onSuccessfullyLoggedIn()

    // ChangePasswordFragment
    fun onPasswordChanged()
}