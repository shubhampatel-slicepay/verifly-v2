package com.slice.verifly.base

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.slice.verifly.utility.*
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    companion object {
        private const val TAG = "BaseFragment"
    }

    val compositeDisposable by lazy { CompositeDisposable() }

    override fun onDetach() {
        compositeDisposable.dispose()
        super.onDetach()
    }

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
                            val alert = AppUtils.showAlertDialog(
                                context,
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
                        onReadPhoneStatePermitted()
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

    open fun onReadPhoneStatePermitted() {
        SlicePayLog.info(TAG, "onReadPhoneStatePermitted")
    }
}