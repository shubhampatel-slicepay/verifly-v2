package com.slice.verifly.features.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.home.communicator.HomeCommunicator
import com.slice.verifly.features.home.enums.HomeTransaction
import com.slice.verifly.features.home.viewmodel.DashboardFragmentViewModel
import com.slice.verifly.features.home.enums.DashboardScreen
import com.slice.verifly.models.tasksorganized.TasksOrganizedModel
import com.slice.verifly.utility.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment: BaseFragment() {

    companion object {
        fun newInstance() = DashboardFragment()
        private val TAG = "DashboardFragment"
    }

    // Components

    private val viewModel: DashboardFragmentViewModel by viewModel()

    // Properties

    private var communicator: HomeCommunicator? = null
    private var screenId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeCommunicator) communicator = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
        communicator?.setUpToolbar("Dashboard", showNavIcon = false, showExpandBtn = false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAssignedTasks()
        tv_newStatus.setOnClickListener { loadScreen(DashboardScreen.NEW) }
        tv_ongoingStatus.setOnClickListener { loadScreen(DashboardScreen.ONGOING) }
        tv_completedStatus.setOnClickListener { loadScreen(DashboardScreen.COMPLETED) }
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

    private fun getAssignedTasks() {
        if (activity?.isNetworkConnected == true) {
            viewModel.getAssignedTasks().observe(this, Observer { response ->
                response?.let {
                    if (it.isNotEmpty()) {
                        tv_dashboardBlank.visibility = View.GONE
                        cl_dashboardContents.visibility = View.VISIBLE
                        loadScreen(DashboardScreen.NEW)
                    } else {
                        fl_dashboardRootContainer.snack(Constants.ERROR_FETCHING_DATA_MESSAGE).show()
                        cl_dashboardContents.visibility = View.GONE
                        tv_dashboardBlank.visibility = View.VISIBLE
                    }
                }
            })
        } else {
            viewModel.noInternet()
        }
    }

    // Operations

    private fun loadScreen(dashboardScreen: DashboardScreen) {
        if (screenId != dashboardScreen.ordinal) {
            when(dashboardScreen) {
                DashboardScreen.NEW -> {
                    replaceFragment(UsersTasksNewFragment.newInstance(), ll_statusFragmentContainer.id, false)
                }

                DashboardScreen.ONGOING -> {
                    replaceFragment(UsersTasksOngoingFragment.newInstance(), ll_statusFragmentContainer.id, false)
                }

                DashboardScreen.COMPLETED -> {
                    replaceFragment(UsersTasksCompletedFragment.newInstance(), ll_statusFragmentContainer.id, false)
                }
            }
            screenId == dashboardScreen.ordinal
        }
    }

    // Communicator & Callbacks

    fun onUserTaskSelected(userTask: TasksOrganizedModel) {
        val bundle = Bundle().apply {
            putParcelable(Constants.USER_TASK_SELECTED, userTask)
        }
        communicator?.transact(HomeTransaction.DASHBOARD_TO_TASKSLIST, bundle)
    }
}