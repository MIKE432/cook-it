package com.apusart.cookit.ui.main_activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    val currentItem = MutableLiveData(0)
}