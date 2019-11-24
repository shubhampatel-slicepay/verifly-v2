package com.slice.verifly.features.home.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.slice.verifly.R
import com.slice.verifly.customview.LoadingView
import com.slice.verifly.features.home.viewmodel.HomeActivityViewModel
import com.slice.verifly.features.home.communicator.HomeCommunicator
import com.slice.verifly.features.home.enums.HomeScreen
import com.slice.verifly.features.home.enums.HomeTransaction
import com.slice.verifly.utility.disableScreen
import com.slice.verifly.utility.enableScreen
import com.slice.verifly.utility.toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(),
    HomeCommunicator {

    companion object {
        private const val TAG = "HomeActivity"
    }

    // Components

    private val viewModel: HomeActivityViewModel by viewModel()
    private lateinit var navController: NavController

    // Properties

    private var loadingView: LoadingView? = null

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(homeNavHost.id)
        initToolbar()
        setUpObservers()
    }

    // Operations

    private fun initToolbar() {
        toolbar.navigationIcon = null
        toolbar.title = null
        toolbarNavIcon.setOnClickListener { navController.navigateUp() }
        toolbarExpandBtn.setOnClickListener { expandToolbar() }
    }

    private fun setUpObservers() {
        viewModel.errorLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                toast(it)
            }
        })

        viewModel.noInternetLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                toast(it)
            }
        })
    }

    private fun expandToolbar() {

    }

    // Communicator

    override fun transact(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    override fun transact(@IdRes actionId: Int, bundle: Bundle?) {
        navController.navigate(actionId, bundle)
    }

    override fun transact(homeTransaction: HomeTransaction, bundle: Bundle?) {
        navController.navigate(homeTransaction.actionID, bundle)
    }

    override fun back() {
        navController.navigateUp()
    }

    override fun getCurrentScreen(): HomeScreen? {
        val destinationLabel = navController.currentDestination?.label
        destinationLabel?.let {
            return when {
                it.contains(HomeScreen.DASHBOARD.name) -> HomeScreen.DASHBOARD
                it.contains(HomeScreen.TASKS_LIST.name) -> HomeScreen.TASKS_LIST
                else -> null
            }
        } ?: return null
    }

    override fun refreshCurrentScreen(homeScreen: HomeScreen) {
        navController.currentDestination?.let {
            if (it.label == homeScreen.name) {
                val actionId = it.id
                navController.popBackStack(it.id, true)
                navController.navigate(actionId)
            }
        }
    }

    override fun setUpToolbar(title: String, showNavIcon: Boolean, showExpandBtn: Boolean) {
        toolbarTitle.text = title
        toolbarNavIcon.visibility = if(showNavIcon) View.VISIBLE else View.INVISIBLE
        toolbarExpandBtn.visibility = if(showExpandBtn) View.VISIBLE else View.INVISIBLE
    }

    override fun showLoading() {
        rl_homeRootContainer.alpha = .5f
        loadingView = LoadingView(this)
        loadingView?.showLoading() ?: kotlin.run {
            pb_loading.visibility = View.VISIBLE
        }
        disableScreen()
    }

    override fun hideLoading() {
        enableScreen()
        loadingView?.hideLoading() ?: kotlin.run {
            pb_loading.visibility = View.GONE
        }
        rl_homeRootContainer.alpha = 1f
    }
}