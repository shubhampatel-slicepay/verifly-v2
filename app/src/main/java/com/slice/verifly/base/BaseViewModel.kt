package com.slice.verifly.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val errorLiveData = MutableLiveData<String>()

    val noInternetLiveData = MutableLiveData<String>()
}