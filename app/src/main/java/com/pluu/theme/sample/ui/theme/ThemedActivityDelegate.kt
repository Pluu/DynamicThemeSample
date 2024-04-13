package com.pluu.theme.sample.ui.theme

import com.pluu.theme.sample.di.ApplicationScope
import com.pluu.theme.sample.domain.GetThemeUseCase
import com.pluu.theme.sample.domain.GetUseCustomThemeUseCase
import com.pluu.theme.sample.domain.ObserveThemeModeUseCase
import com.pluu.theme.sample.domain.ObserveUseCustomThemeModeUseCase
import com.pluu.theme.sample.domain.result.successOr
import com.pluu.theme.sample.model.Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

interface ThemedActivityDelegate {
    val theme: Flow<Theme>
    val currentTheme: Theme

    val useCustomTheme: Flow<Boolean>
    val isUsedCustomTheme: Boolean
}

class ThemedActivityDelegateImpl @Inject constructor(
    @ApplicationScope externalScope: CoroutineScope,
    observeUseCustomThemeModeUseCase: ObserveUseCustomThemeModeUseCase,
    private val isUseCustomThemeUseCase: GetUseCustomThemeUseCase,
    observeThemeUseCase: ObserveThemeModeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
) : ThemedActivityDelegate {

    override val theme: Flow<Theme> = combine(
        observeUseCustomThemeModeUseCase(Unit),
        observeThemeUseCase(Unit)
    ) { isUseCustomTheme, theme ->
        findTheme(isUseCustomTheme.successOr(false), theme.successOr(Theme.Default))
    }.stateIn(externalScope, SharingStarted.WhileSubscribed(5_000), Theme.Default)

    override val isUsedCustomTheme: Boolean
        get() = runBlocking {
            isUseCustomThemeUseCase(Unit).successOr(false)
        }

    override val currentTheme: Theme
        get() = runBlocking {
            findTheme(isUsedCustomTheme, getThemeUseCase(Unit).successOr(Theme.Default))
        }

    override val useCustomTheme: Flow<Boolean> =
        observeUseCustomThemeModeUseCase(Unit).mapLatest {
            it.successOr(false)
        }

    private fun findTheme(isUseCustomTheme: Boolean, theme: Theme): Theme {
        return if (isUseCustomTheme) {
            theme
        } else {
            Theme.Default
        }
    }
}
