package com.slice.verifly.features.home.dashboard.userstaskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.home.dashboard.DashboardFragment
import com.slice.verifly.features.home.dashboard.DashboardFragmentViewModel
import com.slice.verifly.models.tasksorganized.TasksOrganizedModel
import kotlinx.android.synthetic.main.fragment_users_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersTasksOngoingFragment: BaseFragment(), UsersTasksRecyclerAdapterCallback {

    companion object {
        private val TAG = "UsersTasksOngoingFragment"
        fun newInstance() = UsersTasksOngoingFragment()
    }

    // Components

    private val viewModel: DashboardFragmentViewModel by viewModel()

    // Properties

    private var adapter: UsersTasksRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.getOngoingUsersTasks().observe(this, Observer { response ->
            response?.let {
                if (it.isNotEmpty()) {
                    populateRecycler(it)
                }
            }
        })
    }

    // Operations

    private fun populateRecycler(data: List<TasksOrganizedModel>) {
        if (adapter == null) {
            adapter = UsersTasksRecyclerAdapter(data, this)
        }
        with(rv_usersTasksList) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
            adapter = this@UsersTasksOngoingFragment.adapter
        }
    }

    // Adapter callbacks

    override fun onUserTaskSelected(task: TasksOrganizedModel?) {
        parentFragment?.let { fragment ->
            if (fragment is DashboardFragment) {
                task?.let { userTask ->
                    (parentFragment as DashboardFragment).onUserTaskSelected(userTask)
                }
            }
        }
    }
}