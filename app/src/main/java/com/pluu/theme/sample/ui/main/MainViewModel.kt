package com.pluu.theme.sample.ui.main

import androidx.lifecycle.ViewModel
import com.pluu.theme.sample.ui.theme.ThemedActivityDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    themedActivityDelegate: ThemedActivityDelegate,
) : ViewModel(),
    ThemedActivityDelegate by themedActivityDelegate {

}