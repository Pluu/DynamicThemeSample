package com.pluu.theme.sample.ui.theme

import com.pluu.theme.sample.di.ApplicationScope
import com.pluu.theme.sample.domain.GetThemeUseCase
import com.pluu.theme.sample.domain.ObserveThemeModeUseCase
import com.pluu.theme.sample.domain.result.Result
import com.pluu.theme.sample.domain.result.successOr
import com.pluu.theme.sample.model.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface ThemedActivityDelegate {
    val theme: StateFlow<Theme>

    val currentTheme: Theme
}

class ThemedActivityDelegateImpl @Inject constructor(
    @ApplicationScope externalScope: CoroutineScope,
    observeThemeUseCase: ObserveThemeModeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : ThemedActivityDelegate {

    override val theme: StateFlow<Theme> = observeThemeUseCase(Unit).map {
        it.successOr(Theme.DEFAULT)
    }.stateIn(externalScope, SharingStarted.Eagerly, Theme.DEFAULT)

    override val currentTheme: Theme
        get() = runBlocking { // Using runBlocking to execute this coroutine synchronously
            getThemeUseCase(Unit).let {
                if (it is Result.Success) it.data else Theme.DEFAULT
            }
        }
}
