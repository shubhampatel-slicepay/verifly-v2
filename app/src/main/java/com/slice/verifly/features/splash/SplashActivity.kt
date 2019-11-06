package com.slice.verifly.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slice.verifly.R
import com.slice.verifly.features.login.LoginActivity
import com.slice.verifly.features.splash.mainsplash.SplashFragment
import com.slice.verifly.utility.SlicePayLog
import com.slice.verifly.utility.addFragment
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: AppCompatActivity(), SplashCommunicator {

    companion object {
        private val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showSplashScreen()
    }

    private fun showSplashScreen() {
        addFragment(SplashFragment.newInstance(), rl_splash.id, addToStack = false)
    }

    // Communicator & Callbacks

    override fun onSplashAnimationFinished() {
        val intent = Intent(this, LoginActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    override fun dismissActivity() {
        SlicePayLog.info(TAG, "dismissActivity")
        finish()
    }
}