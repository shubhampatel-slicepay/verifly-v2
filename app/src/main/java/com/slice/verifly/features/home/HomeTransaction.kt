package com.slice.verifly.features.home

import com.slice.verifly.R

enum class HomeTransaction(val actionID: Int) {

    DASHBOARD_TO_TASKSLIST(R.id.action_dashboardFragment_to_tasksListFragment),

    TASKSLIST_TO_TASKDETAILS(R.id.action_tasksListFragment_to_taskDetailsFragment)
}