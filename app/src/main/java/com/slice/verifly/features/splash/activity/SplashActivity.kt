package com.slice.verifly.features.splash.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slice.verifly.R
import com.slice.verifly.features.home.activity.HomeActivity
import com.slice.verifly.features.login.activity.LoginActivity
import com.slice.verifly.features.splash.communicator.SplashCommunicator
import com.slice.verifly.features.splash.fragment.SplashFragment
import com.slice.verifly.utility.SlicePayLog
import com.slice.verifly.utility.addFragment
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: AppCompatActivity(),
    SplashCommunicator {

    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showSplashScreen()
    }

    // Operations

    private fun showSplashScreen() {
        addFragment(SplashFragment.newInstance(), rl_splash.id, addToStack = false)
    }

    private fun lunchLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    private fun lunchHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    // Communicator & Callbacks

    override fun onSplashAnimationFinished(status: Boolean) {
        if (status) {
            lunchHomeActivity()
        } else {
            lunchLoginActivity()
        }
    }

    override fun dismissActivity() {
        SlicePayLog.info(TAG, "dismissActivity")
        finish()
    }
}