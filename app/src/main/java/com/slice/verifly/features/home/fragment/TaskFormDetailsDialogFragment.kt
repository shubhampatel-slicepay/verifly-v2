package com.slice.verifly.features.home.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slice.verifly.R
import com.slice.verifly.features.home.base.BaseDialogFragment
import com.slice.verifly.features.home.communicator.TaskFormDetailsDialogCallback
import com.slice.verifly.features.home.communicator.UiComponentCommunicator
import com.slice.verifly.features.home.enums.TaskForm
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.AppUtils
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.FileUtils
import com.slice.verifly.utility.snack
import kotlinx.android.synthetic.main.dialog_fragment_task_form_details.*

class TaskFormDetailsDialogFragment: BaseDialogFragment(), UiComponentCommunicator {

    companion object {
        private const val TAG = "TaskFormDetailsDialogFragment"
        fun newInstance() = TaskFormDetailsDialogFragment()
        private val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        private const val permissionsRequestCode = Constants.MEDIA_PER_REQ_CODE
        private const val MEDIA_REQ_CODE = "mediaReqCode"
        private const val MEDIA_FILE_PATH = "mediaFilePath"
    }

    // Properties

    private var callback: TaskFormDetailsDialogCallback? = null
    private var task: TaskDocuments? = null
    private var taskForm: TaskForm? = null
    private var mediaReqCode: Int? = null
    private var mediaFilePath: String? = null

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        if ((targetRequestCode == Constants.TASK_FORM_DETAILS_DIALOG_REQ)
            and (targetFragment is TaskFormDetailsDialogCallback)) {
            callback = targetFragment as TaskFormDetailsDialogCallback
        }
        arguments?.let {
            if (it.containsKey(Constants.TASK_SELECTED)) {
                task = it[Constants.TASK_SELECTED] as TaskDocuments
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_task_form_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDialog()
        setListeners()
        task?.let {
            activity?.loadTaskFormContents(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mediaReqCode?.let {
            outState.putInt(MEDIA_REQ_CODE, it)
        }
        mediaFilePath?.let {
            outState.putString(MEDIA_FILE_PATH, it)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if (savedInstanceState?.containsKey(MEDIA_REQ_CODE) == true) {
            mediaReqCode = savedInstanceState.getInt(MEDIA_REQ_CODE)
        }
        if (savedInstanceState?.containsKey(MEDIA_FILE_PATH) == true) {
            mediaFilePath = savedInstanceState.getString(MEDIA_FILE_PATH)
        }
        super.onViewStateRestored(savedInstanceState)
    }

    // OS callbacks

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constants.OSV_SELFIE_MEDIA_REQ_CODE, Constants.OSV_PAN_MEDIA_REQ_CODE,
            Constants.OSV_FRONT_ADDRESS_PROOF_MEDIA_REQ_CODE, Constants.OSV_REAR_ADDRESS_PROOF_MEDIA_REQ_CODE,
            Constants.OSV_PHOTO_OF_USER_MEDIA_REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    extract(requestCode, data)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // Operations

    private fun setUpDialog() {
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                callback?.onSoftBackButtonPressed(true)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun setListeners() {
        iv_formClose.setOnClickListener {
            callback?.onDismissed()
        }
        tv_formSubmitButton.setOnClickListener {
            taskForm?.submitForm()
        }
    }

    private fun Context.loadTaskFormContents(task: TaskDocuments) {
        val component = when (task.taskID) {
            TaskForm.OPS0007.name -> {
                taskForm = TaskForm.OPS0007
                taskForm?.showForm(
                    context = this,
                    task = task,
                    communicator = this@TaskFormDetailsDialogFragment
                )
            }

            else -> {
                null
            }
        }
        component?.let {
            ll_formContainer.visibility = View.VISIBLE
            ll_formContainer.addView(it)
        } ?: kotlin.run {
            ll_formContainer.visibility = View.GONE
        }
    }

    private fun openPicker(reqCode: Int) {
        if (askRunTimePermissions(permissions, permissionsRequestCode)) {
            when (reqCode) {
                Constants.OSV_SELFIE_MEDIA_REQ_CODE, Constants.OSV_PAN_MEDIA_REQ_CODE,
                Constants.OSV_FRONT_ADDRESS_PROOF_MEDIA_REQ_CODE, Constants.OSV_REAR_ADDRESS_PROOF_MEDIA_REQ_CODE,
                Constants.OSV_PHOTO_OF_USER_MEDIA_REQ_CODE -> {
                    AppUtils.dispatchGalleryIntent(
                        imagesRequired = true,
                        multiplesRequired = false,
                        uri = null
                    )?.let { target ->
                        val (extraIntent, currentFilePath) = AppUtils.dispatchCameraIntent(activity)
                        this.mediaFilePath = currentFilePath
                        extraIntent?.let {
                            val intentChooser = AppUtils.createIntentChooser(target, "Select an option", arrayOf(it))
                            startActivityForResult(intentChooser, reqCode)
                        }
                    }
                }
            }
        }
    }

    private fun extract(requestCode: Int, intentData: Intent?) {
        /*val filePath = intentData?.data?.let { // gallery
            activity?.let { context -> FileUtils.getRealPath(context, it) }
        } ?: mediaFilePath // camera
        filePath?.let {
            checkAndUpload( requestCode, it)
        }*/

        {
            intentData?.data?.let {
                activity?.let { context -> FileUtils.getRealPath(context, it) }
            } ?: mediaFilePath
        } ()?.let {
            checkAndUpload( requestCode, it)
        }
    }

    private fun checkAndUpload(requestCode: Int, filePath: String) {
        val (status, errorMessage) = FileUtils.verifyFile(filePath, sizeRequired = 5)
        if (status) {
            taskForm?.upload(requestCode, filePath)
        } else {
            errorMessage?.let { showSnack(it) }
        }
    }

    // Communicator

    override fun onMediaAccessRequested(reqCode: Int) {
        this.mediaReqCode = reqCode
        openPicker(reqCode)
    }

    override fun uploadFilesToCloudinary(files: Array<String?>) {
        while (files.isNotEmpty()) {

        }
    }

    // base function overridden to get notified

    override fun onMediaAccessPermitted() {
        super.onMediaAccessPermitted()
        mediaReqCode?.let { openPicker(it) }
    }

    override fun showSnack(message: String) {
        super.showSnack(message)
        cl_taskFormDetailsRootContainer.snack(message)
            .setDuration(5000)
            .setAction("OK") { AppUtils.openSettings(activity) }
            .show()
    }
}