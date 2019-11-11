package com.slice.verifly.features.home.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.slice.verifly.R
import com.slice.verifly.features.home.communicator.TaskFormDetailsDialogCallback
import com.slice.verifly.features.home.enums.TaskForm
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.Constants
import kotlinx.android.synthetic.main.dialog_fragment_task_form_details.*

class TaskFormDetailsDialogFragment: DialogFragment() {

    companion object {
        private const val TAG = "TaskFormDetailsDialogFragment"
        fun newInstance() = TaskFormDetailsDialogFragment()
    }

    // Properties

    private var callback: TaskFormDetailsDialogCallback? = null
    private var task: TaskDocuments? = null

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        if ((targetRequestCode == Constants.TASK_FORM_DETAILS_DIALOG_REQ)
            and (targetFragment is TaskFormDetailsDialogCallback)) {
            callback = targetFragment as TaskFormDetailsDialogCallback
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
        iv_formClose.setOnClickListener {
            callback?.onDismissed()
        }
        loadTaskFormContents()
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                callback?.onSoftBackButtonPressed(true)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    // Operations

    private fun loadTaskFormContents() {
        task?.let {
            when (it.taskID) {
                "ops0007" -> {
                    if (it.taskStatus.equals(Constants.COMPLETED_STATUS)) {
                        TaskForm.OPS0007.loadUnEditableForm(it)
                    } else {
                        TaskForm.OPS0007.loadEditableForm(it)
                    }
                }
            }
        }
    }
}