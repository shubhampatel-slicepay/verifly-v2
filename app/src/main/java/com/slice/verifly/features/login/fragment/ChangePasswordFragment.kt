package com.slice.verifly.features.login.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding3.widget.textChanges
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.login.viewmodel.ChangePasswordViewModel
import com.slice.verifly.features.login.communicator.LoginCommunicator
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.isNetworkConnected
import com.slice.verifly.utility.snack
import com.slice.verifly.utility.toast
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment: BaseFragment() {

    companion object {
        fun newInstance() = ChangePasswordFragment()
        private const val TAG = "ChangePasswordFragment"
    }

    // Components

    private val viewModel: ChangePasswordViewModel by viewModel()

    // Properties

    private var communicator: LoginCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginCommunicator) communicator = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWatchers()
        initDefaultView()
        button_changePassword.setOnClickListener { changePassword() }
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
                    tiet_changePasswordNew.textChanges().map {
                        it.trim().toString()
                    },
                    tiet_changePasswordConfirm.textChanges().map {
                        it.trim().toString()
                    },
                    BiFunction<String, String, Boolean> { t1, t2 -> isInValidCredentials(t1, t2) }
                ).subscribe {
                    button_changePassword.isEnabled = !it
                }
        )
    }

    private fun initDefaultView() {
        til_changePasswordNew.isErrorEnabled = false
        til_changePasswordNew.error = null
        til_changePasswordConfirm.isErrorEnabled = false
        til_changePasswordConfirm.error = null
        button_changePassword.isEnabled = false
    }

    // Operations

    private fun changePassword() {
        val password = tiet_changePasswordNew.text?.trim().toString()
        if (activity?.isNetworkConnected == true) {
            viewModel.changePassword(password).observe(this, Observer { response ->
                response?.let {
                    if (it == Constants.SUCCESS_RESPONSE_STATUS) {
                        cl_changePasswordRootContainer.snack("Password was reset successfully.").show()
                        communicator?.onPasswordChanged()
                    } else {
                        viewModel.notifyError(Constants.RETRY_TOAST_MESSAGE)
                    }
                }
            })
        } else {
            viewModel.noInternet()
        }
    }

    // Utility methods

    private fun isInValidCredentials(newPassword: String, confirmPassword: String): Boolean {
        if (newPassword.isEmpty()) {
            til_changePasswordNew.isErrorEnabled = true
            til_changePasswordNew.error = "Please enter a new password."
        } else {
            til_changePasswordNew.isErrorEnabled = false
            til_changePasswordNew.error = null
        }
        when {
            confirmPassword.isEmpty() -> {
                til_changePasswordConfirm.isErrorEnabled = true
                til_changePasswordConfirm.error = "Please confirm the password."
            }
            (confirmPassword != newPassword) -> {
                til_changePasswordConfirm.isErrorEnabled = true
                til_changePasswordConfirm.error = "Passwords do not match."
            }
            else -> {
                til_changePasswordConfirm.isErrorEnabled = false
                til_changePasswordConfirm.error = null
            }
        }
        return til_changePasswordNew.isErrorEnabled or til_changePasswordConfirm.isErrorEnabled
    }
}