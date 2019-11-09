package com.slice.verifly.features.home.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.home.communicator.UsersTasksRecyclerAdapterCallback
import com.slice.verifly.features.home.adapter.UsersTasksRecyclerAdapter
import com.slice.verifly.features.home.communicator.HomeCommunicator
import com.slice.verifly.features.home.enums.HomeTransaction
import com.slice.verifly.features.home.models.UsersTasksData
import com.slice.verifly.features.home.viewmodel.HomeActivityViewModel
import com.slice.verifly.utility.Constants
import kotlinx.android.synthetic.main.fragment_users_tasks.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersTasksOngoingFragment: BaseFragment(),
    UsersTasksRecyclerAdapterCallback {

    companion object {
        private const val TAG = "UsersTasksOngoingFragment"
        fun newInstance() = UsersTasksOngoingFragment()
    }

    // Components

    private val viewModel: HomeActivityViewModel by viewModel()

    // Properties

    private var communicator: HomeCommunicator? = null
    private var adapter: UsersTasksRecyclerAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeCommunicator) communicator = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    // Operations

    private fun getData() {
        viewModel.assignedTasksData?.ongoingTasks?.let {
            rv_usersTasksList.visibility = View.VISIBLE
            tv_usersTasksBlankText.visibility = View.GONE
            populateRecycler(it)
        } ?: kotlin.run {
            rv_usersTasksList.visibility = View.GONE
            tv_usersTasksBlankText.visibility = View.VISIBLE
            tv_usersTasksBlankText.text = Constants.NO_ONGOING_TASKS_MESSAGE
        }
    }

    private fun populateRecycler(data: List<UsersTasksData>) {
        if (adapter == null) {
            adapter = UsersTasksRecyclerAdapter(data, this)
        }
        with(rv_usersTasksList) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@UsersTasksOngoingFragment.adapter
        }
    }

    // Adapter callbacks

    override fun onUserTaskSelected(task: UsersTasksData?) {
        val bundle = Bundle().apply {
            putParcelable(Constants.USER_TASK_SELECTED, task)
        }
        communicator?.transact(HomeTransaction.DASHBOARD_TO_TASKSLIST, bundle)
    }
}