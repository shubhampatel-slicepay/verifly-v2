package com.slice.verifly.features.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.slice.verifly.R
import com.slice.verifly.features.home.base.HomeBaseFragment
import com.slice.verifly.features.home.communicator.TaskFormDetailsDialogCallback
import com.slice.verifly.features.home.communicator.TasksRecyclerAdapterCallback
import com.slice.verifly.features.home.models.UsersTasksData
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.Constants

class TasksListFragment: HomeBaseFragment(),
    TasksRecyclerAdapterCallback, TaskFormDetailsDialogCallback {

    companion object {
        private const val TAG = "TasksListFragment"
    }

    // Components

    private val args: TasksListFragmentArgs by navArgs()

    // Properties

    private var taskFormDetailsDialog: TaskFormDetailsDialogFragment? = null
    private var userTask: UsersTasksData? = null

    // Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.userTask = args.userTask
        loadScreen()
    }

    // Parent abstract functions

    override fun setUpToolbar() {
        communicator?.setUpToolbar("Tasks", showNavIcon = true, showExpandBtn = false)
    }

    // Operations

    private fun loadScreen() {

    }

    private fun showTaskFormDetailsScreen(task: TaskDocuments) {
        val tag = StringBuilder()
            .append("$TAG -> TaskFormDetailsDialogFragment").toString()
        val ft = this.fragmentManager?.beginTransaction()
        this.fragmentManager?.findFragmentByTag(tag)?.let {
            ft?.remove(it)
        }
        taskFormDetailsDialog = null
        taskFormDetailsDialog = TaskFormDetailsDialogFragment.newInstance().apply {
            isCancelable = false
            setTargetFragment(this@TasksListFragment, Constants.TASK_FORM_DETAILS_DIALOG_REQ)
            arguments = Bundle().apply {
                putParcelable(Constants.TASK_SELECTED, task)
            }
        }
        ft?.let {
            taskFormDetailsDialog?.show(it, tag)
        }
    }

    // Adapter callbacks

    override fun onTaskSelected(task: TaskDocuments?) {
        task?.let {
            showTaskFormDetailsScreen(it)
        }
    }

    // Dialog callbacks

    override fun onDismissed() {
        taskFormDetailsDialog?.dismiss()
    }

    override fun onSoftBackButtonPressed(dismiss: Boolean) {
        if (dismiss) {
            onDismissed()
        }
    }
}