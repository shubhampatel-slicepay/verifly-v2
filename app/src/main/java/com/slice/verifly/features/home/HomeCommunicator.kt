package com.slice.verifly.features.home

import android.os.Bundle

interface HomeCommunicator {

    fun setUpToolbar(title: String, showNavIcon: Boolean, showExpandBtn: Boolean)

    fun transact(navigationAction: HomeTransaction, bundle: Bundle? = null)
}