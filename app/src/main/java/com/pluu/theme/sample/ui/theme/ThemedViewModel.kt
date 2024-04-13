package com.pluu.theme.sample.ui.theme

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemedViewModel @Inject constructor(
    themedActivityDelegate: ThemedActivityDelegate,
) : ViewModel(),
    ThemedActivityDelegate by themedActivityDelegate