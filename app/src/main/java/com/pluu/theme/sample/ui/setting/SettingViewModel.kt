package com.pluu.theme.sample.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluu.theme.sample.domain.SetThemeUseCase
import com.pluu.theme.sample.domain.SetUseCustomThemeUseCase
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.ui.base.ThemedActivityDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val themedActivityDelegate: ThemedActivityDelegate,
    private val setUseCustomThemeUseCase: SetUseCustomThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : ViewModel(), ThemedActivityDelegate by themedActivityDelegate {
    fun setUseCustomTheme(isUse: Boolean) {
        viewModelScope.launch {
            setUseCustomThemeUseCase(isUse)
        }
    }

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }
}