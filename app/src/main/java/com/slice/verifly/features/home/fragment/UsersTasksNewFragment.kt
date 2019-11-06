package com.slice.verifly.features.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.home.communicator.UsersTasksRecyclerAdapterCallback
import com.slice.verifly.features.home.adapter.UsersTasksRecyclerAdapter
import com.slice.verifly.features.home.viewmodel.DashboardFragmentViewModel
import com.slice.verifly.features.home.models.TasksOrganizedModel
import kotlinx.android.synthetic.main.fragment_users_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersTasksNewFragment: BaseFragment(),
    UsersTasksRecyclerAdapterCallback {

    companion object {
        private val TAG = "UsersTasksNewFragment"
        fun newInstance() = UsersTasksNewFragment()
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
        viewModel.newUsersTasksList?.let {
            populateRecycler(it)
        }
    }

    // Operations

    private fun populateRecycler(data: List<TasksOrganizedModel>) {
        if (adapter == null) {
            adapter = UsersTasksRecyclerAdapter(data, this)
        }
        with(rv_usersTasksList) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@UsersTasksNewFragment.adapter
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