package com.slice.verifly.features.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.slice.verifly.R
import com.slice.verifly.base.BaseFragment

class TasksListFragment: BaseFragment() {

    companion object {
        private const val TAG = "TasksListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateRecycler()
    }

    private fun populateRecycler() {

    }
}