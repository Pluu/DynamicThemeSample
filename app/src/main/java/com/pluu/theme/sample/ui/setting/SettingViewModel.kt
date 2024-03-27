package com.pluu.theme.sample.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluu.theme.sample.domain.SetThemeUseCase
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.ui.theme.ThemedActivityDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val setThemeUseCase: SetThemeUseCase,
    themedActivityDelegate: ThemedActivityDelegate,
) : ViewModel(),
    ThemedActivityDelegate by themedActivityDelegate {

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }
}