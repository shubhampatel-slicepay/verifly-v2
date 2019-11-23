package com.slice.verifly.features.home.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.slice.verifly.R
import com.slice.verifly.features.home.base.BaseUiComponent
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
    private var uiComponent: BaseUiComponent? = null

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
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                callback?.onSoftBackButtonPressed(true)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        iv_formClose.setOnClickListener {
            callback?.onDismissed()
        }
        loadTaskFormContents()
    }

    // Operations

    private fun loadTaskFormContents() {
        task?.let { task ->
            when (task.taskID) {
                TaskForm.OPS0007.name -> {
                    activity?.let { context ->
                        uiComponent = TaskForm.OPS0007.showForm(context = context, task = task)
                        uiComponent?.let { ll_formContainer.addView(it) }
                    }
                }

                else -> {

                }
            }
        }
    }
}