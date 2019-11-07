package com.slice.verifly.features.login.fragment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.widget.textChanges
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.login.communicator.LoginCommunicator
import com.slice.verifly.features.login.viewmodel.LoginFragmentViewModel
import com.slice.verifly.utility.*
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
        private const val TAG = "LoginFragment"
        val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE)
    }

    // Components

    private val viewModel: LoginFragmentViewModel by viewModel()

    // Properties

    private var communicator: LoginCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginCommunicator) communicator = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWatchers()
        initDefaultView()
        button_login.setOnClickListener { proceedToLogIn() }
        tv_loginForgotPassword.setOnClickListener { proceedToResetPassword() }
    }

    private fun setUpObservers() {
        viewModel.errorLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                activity?.toast(it)
            }
        })

        viewModel.noInternetLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                activity?.toast(it)
            }
        })
    }

    private fun setWatchers() {
        compositeDisposable.add(
            Observable
                .combineLatest(
                    tiet_loginNumber.textChanges().map {
                        it.trim().toString()
                    },
                    tiet_loginPassword.textChanges().map {
                        it.trim().toString()
                    },
                    BiFunction<String, String, Boolean> { t1, t2 -> isInValidCredentials(t1, t2) }
                ).subscribe {
                    button_login.isEnabled = !it
                }
        )
    }

    private fun initDefaultView() {
        til_loginNumber.isErrorEnabled = false
        til_loginNumber.error = null
        til_loginPassword.isErrorEnabled = false
        til_loginPassword.error = null
        button_login.isEnabled = false
    }

    // Operations

    private fun proceedToLogIn() {
        val userNumber = tiet_loginNumber.text?.trim().toString()
        val userPassword = tiet_loginPassword.text?.trim().toString()
        if (!isInValidCredentials(userNumber, userPassword)) {
            if (!ifHasPermission(*permissions)) {
                askRunTimePermissions(permissions = *permissions, requestCode = Constants.READ_PHONE_STATE_PER_REQ_CODE)
            } else {
                try {
                    val tm = activity?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    logIn(userNumber, userPassword, tm)

                } catch (e: Exception) {
                    SlicePayLog.info(TAG, e.message)
                }
            }
        }
    }

    private fun proceedToResetPassword() {
        communicator?.showResetPasswordScreen()
    }

    // Network calls

    private fun logIn(
        userNumber: String,
        userPassword: String,
        tm: TelephonyManager
    ) {
        if (activity?.isNetworkConnected == true) {
            viewModel.logIn(userNumber, userPassword, tm).observe(this, Observer { response ->
                response?.let {
                    if (it == Constants.SUCCESS_RESPONSE_STATUS) {
                        cl_loginRootContainer.snack("Logged in successfully.").show()
                        communicator?.onSuccessfullyLoggedIn()
                    } else {
                        viewModel.notifyOnError(Constants.RETRY_TOAST_MESSAGE)
                    }
                }
            })
        } else {
            viewModel.noInternet()
        }
    }

    // Base to Child communication

    override fun onReadPhoneStatePermission() {
        proceedToLogIn()
    }

    // Utility methods

    private fun isInValidCredentials(number: String, password: String): Boolean {
        when {
            number.isEmpty() -> {
                til_loginNumber.isErrorEnabled = true
                til_loginNumber.error = "Please enter number."
            }
            (number.length != 10) or (!Patterns.PHONE.matcher(number).matches()) -> {
                til_loginNumber.isErrorEnabled = true
                til_loginNumber.error = "Please enter valid number."
            }
            else -> {
                til_loginNumber.isErrorEnabled = false
                til_loginNumber.error = null
            }
        }
        if (password.isEmpty()) {
            til_loginPassword.isErrorEnabled = true
            til_loginPassword.error = "Please enter password."
        } else {
            til_loginPassword.isErrorEnabled = false
            til_loginPassword.error = null
        }
        return til_loginNumber.isErrorEnabled or til_loginPassword.isErrorEnabled
    }
}