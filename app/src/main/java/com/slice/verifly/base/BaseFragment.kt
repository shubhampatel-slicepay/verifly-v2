package com.slice.verifly.base

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.SlicePayLog
import com.slice.verifly.utility.askPermission
import com.slice.verifly.utility.showAlertDialog
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    private val const TAG = "BaseFragment"
    val compositeDisposable by lazy { CompositeDisposable() }

    protected fun askRunTimePermissions(vararg permissions: String, requestCode: Int) {
        askPermission(permissions = *permissions, requestCode = requestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.READ_PHONE_STATE_PER_REQ_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        if (!shouldShowRequestPermissionRationale(permissions[0])) {
                            val alert = context?.showAlertDialog(
                                null,
                                "It seems you have denied some permissions. Allow them at Settings.",
                                cancelable = false,
                                cancelableTouchOutside = false,
                                positiveButton = "OK",
                                positiveAction = { dialog: DialogInterface, _: Int ->
                                    openDeviceSettings()
                                    dialog.dismiss()
                                }
                            )
                            alert?.let {
                                if (!it.isShowing) it.show()
                            }
                        } else {
                            askPermission(permissions = *permissions, requestCode = requestCode)
                        }
                    } else {
                        onReadPhoneStatePermission()
                    }
                }
            }
        }
    }

    private fun openDeviceSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            data = Uri.fromParts("package", context?.packageName, null)
        }
        startActivity(intent)
    }

    open fun onReadPhoneStatePermission() {
        SlicePayLog.info(TAG, "onReadPhoneStatePermission")
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.dispose()
    }
}