package com.pluu.theme.sample.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData("This is dashboard Fragment")
    val text: LiveData<String> = _text

    init {
        Timber.d("HashCode=[${hashCode()}]")
    }
}