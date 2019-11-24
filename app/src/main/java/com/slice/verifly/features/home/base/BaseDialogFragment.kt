package com.slice.verifly.features.home.base

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.SlicePayLog

abstract class BaseDialogFragment: DialogFragment() {

    companion object {
        private const val TAG = "BaseDialogFragment"

        private const val PERMISSIONS_REQ_MSG = "It seems you have denied some permissions. Allow them at Settings."
        private const val CAMERA_REQ_MSG = "Please enable camera permission at Settings."
        private const val STORAGE_REQ_MSG = "Please enable storage permission at Settings."
    }

    private var permissions: Array<String>? = null
    private var permissionsRequestCode: Int? = null

    // calls from child classes

    protected fun askRunTimePermissions(permissions: Array<String>, requestCode: Int): Boolean {
        this.permissions = permissions
        this.permissionsRequestCode = requestCode
        return checkPermissions(this.permissions, this.permissionsRequestCode)
    }

    // operations

    private fun checkPermissions(permissions: Array<String>?, requestCode: Int?): Boolean {
        activity?.let { context ->
            permissions?.let {
                val listPermissionsNeeded = ArrayList<String>()
                for (permission in it) {
                    if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        listPermissionsNeeded.add(permission)
                    }
                }
                if (listPermissionsNeeded.isNotEmpty()) {
                    requestCode?.let { reqCode ->
                        requestPermissions(listPermissionsNeeded.toTypedArray(), reqCode)
                    } ?: return true // permissionsRequestCode = null => permitted
                    return false // listPermissionsNeeded != null => not permitted
                }
                return true // listPermissionsNeeded = null => permitted
            } ?: return true // permissions = null => permitted
        } ?: return false // context = null => not permitted
    }



    // OS callbacks

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.MEDIA_PER_REQ_CODE -> {
                verifyMediaPermissions(permissions, grantResults)
            }
        }
    }

    // Individual OS callback for "requestPermissions" handling

    private fun verifyMediaPermissions(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty()) {
            val permissionResults = HashMap<String, Int>()
            var deniedCount = 0
            for (i in grantResults) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionResults[permissions[i]] = grantResults[i]
                    deniedCount++
                }
            }
            when (deniedCount) {
                0 -> {
                    onMediaAccessPermitted()
                }
                1 -> {
                    val permName = permissionResults.entries.iterator().next().key
                    val permResult = permissionResults[permName]
                    when {
                        shouldShowRequestPermissionRationale(permName) -> checkPermissions(this.permissions, this.permissionsRequestCode)
                        permName == Manifest.permission.CAMERA -> showSnack(CAMERA_REQ_MSG)
                        permName == Manifest.permission.WRITE_EXTERNAL_STORAGE -> showSnack(STORAGE_REQ_MSG)
                    }
                }
                else -> {
                    for (entry in permissionResults.entries) {
                        val permName = entry.key
                        val permResult = entry.value
                        if (shouldShowRequestPermissionRationale(permName)) {
                            checkPermissions(this.permissions, this.permissionsRequestCode)
                        } else {
                            showSnack(PERMISSIONS_REQ_MSG)
                        }
                        break
                    }
                }
            }
        }
    }

    // methods to be overridden by child classes to get notified by this base class

    open fun onMediaAccessPermitted() {
        SlicePayLog.info(TAG, "onMediaAccessPermitted")
    }

    open fun showSnack(message: String) {}
}