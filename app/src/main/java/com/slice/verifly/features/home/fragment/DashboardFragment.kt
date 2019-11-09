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
import com.slice.verifly.features.home.enums.DashboardScreen
import com.slice.verifly.features.home.viewmodel.HomeActivityViewModel
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.utility.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment: BaseFragment() {

    companion object {
        fun newInstance() = DashboardFragment()
        private const val TAG = "DashboardFragment"
    }

    // Components

    private val viewModel: HomeActivityViewModel by viewModel()

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
                    cl_dashboardRootContainer.snack(Constants.ERROR_FETCHING_DATA_MESSAGE).show()
                    ll_usersTasksFragmentContainer.visibility = View.GONE
                    tv_dashboardError.visibility = View.VISIBLE
                }
            })
        } else {
            viewModel.noInternet()
        }
    }

    private fun formatUsersTasks(data: List<Task?>) {
        viewModel.formatUsersTasks(data).observe(this, Observer { response ->
            response?.let {
                communicator?.hideLoading()
                tv_dashboardError.visibility = View.GONE
                ll_usersTasksFragmentContainer.visibility = View.VISIBLE
                loadScreen(DashboardScreen.NEW)
            }
        })
    }

    // Operations

    private fun loadScreen(dashboardScreen: DashboardScreen) {
        if (screenId != dashboardScreen.ordinal) {
            when(dashboardScreen) {
                DashboardScreen.NEW -> {
                    highlightButton(newStatusButton = true)
                    replaceFragment(
                        UsersTasksNewFragment.newInstance(),
                        ll_usersTasksFragmentContainer.id,
                        addToStack = false
                    )
                }

                DashboardScreen.ONGOING -> {
                    highlightButton(ongoingStatusButton = true)
                    replaceFragment(
                        UsersTasksOngoingFragment.newInstance(),
                        ll_usersTasksFragmentContainer.id,
                        addToStack = false
                    )
                }

                DashboardScreen.COMPLETED -> {
                    highlightButton(completedStatusButton = true)
                    replaceFragment(
                        UsersTasksCompletedFragment.newInstance(),
                        ll_usersTasksFragmentContainer.id,
                        addToStack = false
                    )
                }
            }
            screenId = dashboardScreen.ordinal
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
}