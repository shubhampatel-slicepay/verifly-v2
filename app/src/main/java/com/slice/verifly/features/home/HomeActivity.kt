package com.slice.verifly.features.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.slice.verifly.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), HomeCommunicator {

    companion object {
        private val TAG = "HomeActivity"
    }

    // Components

    private val viewModel: HomeActivityViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(homeNavHost.id)
        initToolbar()
    }

    // Operations

    private fun initToolbar() {
        toolbar.navigationIcon = null
        toolbar.title = null
        toolbarNavIcon.setOnClickListener { navController.navigateUp() }
        toolbarExpandBtn.setOnClickListener { expandToolbar() }
    }

    private fun expandToolbar() {

    }

    // Communicator & Callbacks

    override fun transact(navigationAction: HomeTransaction, bundle: Bundle?) {
        navController.navigate(navigationAction.actionID, bundle)
    }

    override fun setUpToolbar(title: String, showNavIcon: Boolean, showExpandBtn: Boolean) {
        toolbarTitle.text = title
        toolbarNavIcon.visibility = if(showNavIcon) View.VISIBLE else View.INVISIBLE
        toolbarExpandBtn.visibility = if(showExpandBtn) View.VISIBLE else View.GONE
    }
}