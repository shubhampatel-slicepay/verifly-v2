package com.slice.verifly.features.login.fragment

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding3.widget.textChanges
import com.slice.verifly.R
import com.slice.verifly.features.login.communicator.ResetPasswordCallback
import com.slice.verifly.features.login.viewmodel.ResetPasswordViewModel
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.isNetworkConnected
import com.slice.verifly.utility.toast
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.layout_reset_password_main.*
import kotlinx.android.synthetic.main.layout_reset_password_otp.*
import kotlinx.android.synthetic.main.layout_reset_password_status.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordBottomSheet: BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = ResetPasswordBottomSheet()
        private const val TAG = "ResetPasswordBottomSheet"
        private val compositeDisposable = CompositeDisposable()
    }

    // Components

    private val viewModel: ResetPasswordViewModel by viewModel()

    // Properties

    private var callback: ResetPasswordCallback? = null
    private var icons: ArrayList<Drawable?>? = null
    private var phoneNumber: String? = null
    private var otpNumber: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ResetPasswordCallback) callback = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_reset_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            icons = arrayListOf(
                ContextCompat.getDrawable(it, R.drawable.ic_check_circle_unconfirmed),
                ContextCompat.getDrawable(it, R.drawable.ic_check_circle_confirmed),
                ContextCompat.getDrawable(it, R.drawable.ic_cancel)
            )
        }
        setWatchers()
        initResetPasswordMain()
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
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

    // Network calls

    private fun sendOtp() {
        this.phoneNumber?.let { phoneNumber ->
            if (activity?.isNetworkConnected == true) {
                viewModel.sendOtp(phoneNumber).observe(this, Observer { response ->
                    response?.let {
                        if (it == Constants.SUCCESS_RESPONSE_STATUS) {
                            activity?.toast("OTP sent successfully.")
                            initResetPasswordOtp()
                        } else {
                            viewModel.notifyError(Constants.RETRY_TOAST_MESSAGE)
                        }
                    }
                })
            } else {
                viewModel.noInternet()
            }
        }
    }

    private fun verifyOtp() {
        if (!phoneNumber.isNullOrEmpty() and !otpNumber.isNullOrEmpty()) {
            if (activity?.isNetworkConnected == true) {
                viewModel.verifyOtp(phoneNumber!!, otpNumber!!).observe(this, Observer {  response ->
                    response?.let {
                        if (it == Constants.SUCCESS_RESPONSE_STATUS) {
                            callback?.onOtpVerified()
                        } else {
                            viewModel.notifyError(Constants.ERROR_TOAST_MESSAGE)
                            initResetPasswordStatus()
                        }
                    }
                })
            } else {
                viewModel.noInternet()
            }
        }
    }

    // View operations

    private fun initResetPasswordMain() {
        cl_resetPasswordMainContainer.visibility = View.VISIBLE
        cl_resetPasswordOtpContainer.visibility = View.GONE
        cl_resetPasswordStatusContainer.visibility = View.GONE
        iv_resetPasswordMainProceedButton.setOnClickListener {
            phoneNumber = tiet_resetPasswordMainRegNumber.text?.trim().toString()
            sendOtp()
        }
    }

    private fun initResetPasswordOtp() {
        cl_resetPasswordMainContainer.visibility = View.GONE
        cl_resetPasswordOtpContainer.visibility = View.VISIBLE
        cl_resetPasswordStatusContainer.visibility = View.GONE
        iv_resetPasswordOtpProceedButton.setOnClickListener {
            otpNumber = tiet_resetPasswordOtpNumber.text?.trim().toString()
            verifyOtp()
        }
        tv_resetPasswordOtpResendButton.setOnClickListener {
            sendOtp()
        }
    }

    private fun initResetPasswordStatus() {
        cl_resetPasswordMainContainer.visibility = View.GONE
        cl_resetPasswordOtpContainer.visibility = View.GONE
        cl_resetPasswordStatusContainer.visibility = View.VISIBLE
        tv_resetPasswordStatusMessage.text = getString(R.string.reset_password_fail_status)
        iv_resetPasswordStatusImage.setImageDrawable(icons?.get(2))
        tv_resetPasswordStatusRetryButton.setOnClickListener {
            phoneNumber = null
            otpNumber = null
            initResetPasswordMain()
        }
    }

    // Utility methods

    private fun setWatchers() {
        val d1 = tiet_resetPasswordMainRegNumber.textChanges().subscribe {
            if (it.trim().toString().length == 10) {
                iv_resetPasswordMainProceedButton.isClickable = true
                iv_resetPasswordMainProceedButton.isEnabled = true
                iv_resetPasswordMainProceedButton.setImageDrawable(icons?.get(1))
            } else {
                iv_resetPasswordMainProceedButton.isClickable = false
                iv_resetPasswordMainProceedButton.isEnabled = false
                iv_resetPasswordMainProceedButton.setImageDrawable(icons?.get(0))
            }
        }

        val d2 = tiet_resetPasswordOtpNumber.textChanges().subscribe {
            if (it.trim().toString().length == 4) {
                iv_resetPasswordOtpProceedButton.isClickable = true
                iv_resetPasswordOtpProceedButton.isEnabled = true
                iv_resetPasswordOtpProceedButton.setImageDrawable(icons?.get(1))
            } else {
                iv_resetPasswordOtpProceedButton.isClickable = false
                iv_resetPasswordOtpProceedButton.isEnabled = false
                iv_resetPasswordOtpProceedButton.setImageDrawable(icons?.get(0))
            }
        }

        compositeDisposable.apply {
            add(d1)
            add(d2)
        }
    }
}