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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import javax.inject.Inject

interface ThemedActivityDelegate {
    val theme: Flow<Theme>
    val currentTheme: Theme

    val useCustomTheme: Flow<Boolean>
    val isUsedCustomTheme: Boolean

    fun refresh()
}

class ThemedActivityDelegateImpl @Inject constructor(
    @ApplicationScope externalScope: CoroutineScope,
    observeUseCustomThemeModeUseCase: ObserveUseCustomThemeModeUseCase,
    private val isUseCustomThemeUseCase: GetUseCustomThemeUseCase,
    observeThemeUseCase: ObserveThemeModeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
) : ThemedActivityDelegate {

    private val refreshSignal = MutableStateFlow(false)

    override val theme: Flow<Theme> = combine(
        observeUseCustomThemeModeUseCase(Unit),
        observeThemeUseCase(Unit),
        refreshSignal,
    ) { isUseCustomTheme, theme, _ ->
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

    override fun refresh() {
        refreshSignal.tryEmit(refreshSignal.value.not())
    }

    private fun findTheme(isUseCustomTheme: Boolean, theme: Theme): Theme {
        return if (isUseCustomTheme) {
            theme
        } else {
            if (LocalTime.now().second % 2 == 0) {
                Theme.Default
            } else {
                Theme.DarkDefault
            }
        }
    }
}
