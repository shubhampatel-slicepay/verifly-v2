package com.slice.verifly.features.login.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slice.verifly.R
import com.slice.verifly.features.home.activity.HomeActivity
import com.slice.verifly.features.login.communicator.LoginCommunicator
import com.slice.verifly.features.login.communicator.ResetPasswordCallback
import com.slice.verifly.features.login.fragment.ChangePasswordFragment
import com.slice.verifly.features.login.fragment.LoginFragment
import com.slice.verifly.features.login.fragment.ResetPasswordBottomSheet
import com.slice.verifly.utility.replaceFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),
    LoginCommunicator,
    ResetPasswordCallback {

    companion object {
        private const val TAG = "LoginActivity"
    }

    // Properties

    private var bottomSheet: ResetPasswordBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showLoginScreen()
    }

    // Operations

    private fun showLoginScreen() {
        replaceFragment(LoginFragment.newInstance(), rl_login.id, addToStack = false)
    }

    private fun showChangePasswordScreen() {
        replaceFragment(ChangePasswordFragment.newInstance(), rl_login.id, addToStack = true)
    }

    private fun lunchHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    // Communicator & Callbacks

    override fun showResetPasswordScreen() {
        bottomSheet = null
        bottomSheet = ResetPasswordBottomSheet.newInstance().apply {
            isCancelable = false
        }
        bottomSheet?.let {
            if (!it.isAdded) {
                it.show(supportFragmentManager, TAG.plus("ResetPassword"))
            }
        }
    }

    override fun onSuccessfullyLoggedIn() {
        lunchHomeActivity()
    }

    override fun onPasswordChanged() {
        this.onBackPressed()
    }

    override fun onOtpVerified() {
        bottomSheet?.dismiss()
        showChangePasswordScreen()
    }
}