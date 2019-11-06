package com.slice.verifly.features.splash.mainsplash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.splash.SplashCommunicator
import com.slice.verifly.utility.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment() {

    companion object {
        fun newInstance() = SplashFragment()
        private val TAG = "SplashFragment"
    }

    // Components

    private val viewModel: SplashFragmentViewModel by viewModel()

    // Properties

    private var communicator: SplashCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SplashCommunicator) communicator = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        authenticate()
    }

    private fun setUpObservers() {
        viewModel.errorLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                activity?.toast(it)
                //communicator?.dismissActivity()
            }
        })

        viewModel.noInternetLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                activity?.toast(it)
            }
        })
    }

    // Network calls

    private fun authenticate() {
        if (activity?.isNetworkConnected == true) {
            viewModel.authenticate().observe(this, Observer { response ->
                response?.let {
                    if (it == Constants.SUCCESS_RESPONSE_STATUS) {
                        checkAppVersion()
                        animateLogo()
                    } else {
                        viewModel.notifyOnError(Constants.ERROR_TOAST_MESSAGE)
                    }
                }
            })
        } else {
            viewModel.noInternet()
        }
    }

    private fun checkAppVersion() {
        if (activity?.isNetworkConnected == true) {
            viewModel.checkAppVersion().observe(this, Observer { response ->
                response?.let {
                    if (!it) {
                        activity?.toast(Constants.UPDATE_MESSAGE)
                    }
                } ?: SlicePayLog.info(TAG, "Version fetch failed.")
            })
        } else {
            viewModel.noInternet()
        }
    }

    // View operation

    private fun animateLogo() {
        Handler().postDelayed({
            communicator?.onSplashAnimationFinished()
        }, 2000)
    }
}