package com.slice.verifly.features.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.slice.verifly.R
import com.slice.verifly.features.home.adapter.UsersTasksRecyclerAdapter
import com.slice.verifly.features.home.base.HomeBaseFragment
import com.slice.verifly.features.home.communicator.UsersTasksRecyclerAdapterCallback
import com.slice.verifly.features.home.enums.DashboardScreen
import com.slice.verifly.features.home.models.UsersTasksData
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.utility.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment: HomeBaseFragment(), UsersTasksRecyclerAdapterCallback {

    companion object {
        private const val TAG = "DashboardFragment"
    }

    // Properties

    private var adapter: UsersTasksRecyclerAdapter? = null
    private var screenId: Int? = null
    private var notifyAdapterStatus: Boolean = false

    // Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAssignedTasks()
        tv_newStatus.setOnClickListener { loadScreen(DashboardScreen.NEW) }
        tv_ongoingStatus.setOnClickListener { loadScreen(DashboardScreen.ONGOING) }
        tv_completedStatus.setOnClickListener { loadScreen(DashboardScreen.COMPLETED) }
    }

    // State maintenance

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(Constants.NOTIFY_ADAPTER_STATUS_KEY, notifyAdapterStatus)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            notifyAdapterStatus = it.getBoolean(Constants.NOTIFY_ADAPTER_STATUS_KEY, false)
        }
    }

    // Parent abstract functions

    override fun setUpToolbar() {
        communicator?.setUpToolbar("Dashboard", showNavIcon = false, showExpandBtn = false)
    }

    // Network calls

    private fun getAssignedTasks() {
        if (activity?.isNetworkConnected == true) {
            communicator?.showLoading()
            viewModel.getAssignedTasks().observe(this, Observer { response ->
                response?.let {
                    if (it.isNotEmpty()) {
                        formatUsersTasks(it)
                    } else {
                        loadScreen(DashboardScreen.NEW)
                    }
                } ?: kotlin.run {
                    communicator?.hideLoading()
                    showErrorScreen()
                }
            })
        } else {
            viewModel.noInternet()
        }
    }

    // Data manipulation operations

    private fun formatUsersTasks(data: List<Task?>) {
        viewModel.formatUsersTasks(data).observe(this, Observer { response ->
            response?.let {
                communicator?.hideLoading()
                loadScreen(DashboardScreen.NEW)
            }
        })
    }

    // Operations

    private fun loadScreen(dashboardScreen: DashboardScreen) {
        if (screenId != dashboardScreen.ordinal) {
            screenId = dashboardScreen.ordinal
            when(dashboardScreen) {
                DashboardScreen.NEW -> {
                    highlightButton(newStatusButton = true)
                    viewModel.assignedTasksData?.newTasks?.let {
                        showTasksScreen()
                        populateRecycler(it, checkNotifyAdapterStatus())
                    } ?: kotlin.run {
                        showNoTasksScreen(Constants.NO_NEW_TASKS_MESSAGE)
                    }
                }

                DashboardScreen.ONGOING -> {
                    highlightButton(ongoingStatusButton = true)
                    viewModel.assignedTasksData?.ongoingTasks?.let {
                        showTasksScreen()
                        populateRecycler(it, checkNotifyAdapterStatus())
                    } ?: kotlin.run {
                        showNoTasksScreen(Constants.NO_ONGOING_TASKS_MESSAGE)
                    }
                }

                DashboardScreen.COMPLETED -> {
                    highlightButton(completedStatusButton = true)
                    viewModel.assignedTasksData?.completedTasks?.let {
                        showTasksScreen()
                        populateRecycler(it, checkNotifyAdapterStatus())
                    } ?: kotlin.run {
                        showNoTasksScreen(Constants.NO_COMPLETED_TASKS_MESSAGE)
                    }
                }
            }
        }
    }

    private fun populateRecycler(data: List<UsersTasksData>, notifyAdapter: Boolean = false) {
        if (notifyAdapter) {
            adapter?.notifyDataChanged(newData = data)
        } else {
            if (adapter == null) {
                adapter = UsersTasksRecyclerAdapter(data, this)
            }
            with(rv_usersTasksList) {
                layoutManager = LinearLayoutManager(activity)
                adapter = this@DashboardFragment.adapter
            }
        }
    }

    // Adapter callbacks

    override fun onUserTaskSelected(userTask: UsersTasksData?) {
        userTask?.let {
            val action = DashboardFragmentDirections.actionDashboardFragmentToTasksListFragment(it)
            communicator?.transact(action)
        }
    }

    // Utility methods

    private fun highlightButton(
        newStatusButton: Boolean = false,
        ongoingStatusButton: Boolean = false,
        completedStatusButton: Boolean = false
    ) {
        if (newStatusButton) {
            tv_newStatus.setBackgroundResource(R.drawable.bg_textview_enabled)
        } else {
            tv_newStatus.setBackgroundResource(R.drawable.bg_textview_disabled)
        }

        if (ongoingStatusButton) {
            tv_ongoingStatus.setBackgroundResource(R.drawable.bg_textview_enabled)
        } else {
            tv_ongoingStatus.setBackgroundResource(R.drawable.bg_textview_disabled)
        }

        if (completedStatusButton) {
            tv_completedStatus.setBackgroundResource(R.drawable.bg_textview_enabled)
        } else {
            tv_completedStatus.setBackgroundResource(R.drawable.bg_textview_disabled)
        }
    }

    private fun showErrorScreen() {
        cl_dashboardRootContainer.snack(Constants.ERROR_FETCHING_DATA_MESSAGE).show()
        rv_usersTasksList.visibility = View.GONE
        tv_usersTasksBlankText.visibility = View.GONE
        tv_dashboardError.visibility = View.VISIBLE
    }

    private fun showNoTasksScreen(message: String) {
        tv_dashboardError.visibility = View.GONE
        rv_usersTasksList.visibility = View.GONE
        tv_usersTasksBlankText.visibility = View.VISIBLE
        tv_usersTasksBlankText.text = message
    }

    private fun showTasksScreen() {
        tv_dashboardError.visibility = View.GONE
        tv_usersTasksBlankText.visibility = View.GONE
        rv_usersTasksList.visibility = View.VISIBLE
    }

    private fun checkNotifyAdapterStatus(): Boolean {
        return if (notifyAdapterStatus) {
            true
        } else {
            notifyAdapterStatus = true
            false
        }
    }
}