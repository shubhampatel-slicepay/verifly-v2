package com.slice.verifly.features.home.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.slice.verifly.base.BaseFragment
import com.slice.verifly.features.home.communicator.HomeCommunicator
import com.slice.verifly.features.home.enums.HomeTransaction
import com.slice.verifly.features.home.viewmodel.HomeActivityViewModel
import com.slice.verifly.utility.toast
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class HomeBaseFragment: BaseFragment() {

    // Components

    val viewModel: HomeActivityViewModel by viewModel()

    // Properties

    var communicator: HomeCommunicator? = null

    // Lifecycle

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeCommunicator) {
            communicator = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
        setUpToolbar()
    }

    // Define common functions here
    // i.e. common functions, common implementations
    // can't be accessed or over-ridden

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

    // Define common functions here
    // i.e. common functions, default implementations
    // can be accessed and over-ridden

    // type-safe navigation arguments
    protected fun navigationController(
        navDirections: NavDirections
    ) {
        activity?.findNavController(homeNavHost.id)?.navigate(navDirections)
    }

    // type-unsafe navigation arguments



    protected fun navigationController(
        @IdRes actionId: Int,
        bundle: Bundle? = null
    ) {
        activity?.findNavController(homeNavHost.id)?.navigate(actionId, bundle)
    }

    protected fun navigationController(
        homeTransaction: HomeTransaction,
        bundle: Bundle? = null
    ) {
        activity?.findNavController(homeNavHost.id)?.navigate(homeTransaction.actionID, bundle)
    }

    // Define default functions here
    // i.e. common functions, different implementations
    // doesn't need mandatory implementations



    // Define abstract functions here
    // i.e. common functions, different implementations
    // needs mandatory implementations

    abstract fun setUpToolbar()
}