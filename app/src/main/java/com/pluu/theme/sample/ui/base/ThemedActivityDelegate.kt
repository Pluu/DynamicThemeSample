package com.pluu.theme.sample.ui.base

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.getSystemService
import com.pluu.theme.sample.di.ApplicationScope
import com.pluu.theme.sample.domain.GetThemeUseCase
import com.pluu.theme.sample.domain.GetUseCustomThemeUseCase
import com.pluu.theme.sample.domain.ObserveThemeModeUseCase
import com.pluu.theme.sample.domain.ObserveUseCustomThemeModeUseCase
import com.pluu.theme.sample.domain.result.successOr
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

interface ThemedActivityDelegate {
    val currentTheme: Theme
    val isUsedCustomTheme: Boolean
    fun refresh()
    fun fetchTheme()
}

@Singleton
class ThemedActivityDelegateImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope private val externalScope: CoroutineScope,
    private val isUseCustomThemeUseCase: GetUseCustomThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    observeUseCustomThemeModeUseCase: ObserveUseCustomThemeModeUseCase,
    observeThemeUseCase: ObserveThemeModeUseCase,
) : ThemedActivityDelegate {

    private val refreshSignal = MutableStateFlow(false)

    init {
        combine(
            refreshSignal,
            observeUseCustomThemeModeUseCase(Unit),
            observeThemeUseCase(Unit)
        ) { _, isUsedCustomTheme, currentTheme ->
            findTheme(
                isUsedCustomTheme.successOr(false),
                currentTheme.successOr(Theme.Default)
            )
        }.distinctUntilChanged()
            .onEach { theme ->
//                updateAppTheme(context, theme)
            }.launchIn(externalScope + Dispatchers.Main.immediate)
    }

    override val currentTheme: Theme
        get() = runBlocking {
            findTheme(
                isUseCustomThemeUseCase(Unit).successOr(false),
                getThemeUseCase(Unit).successOr(Theme.Default)
            )
        }

    override val isUsedCustomTheme: Boolean
        get() = runBlocking {
            isUseCustomThemeUseCase(Unit).successOr(false)
        }

    override fun refresh() {
        refreshSignal.tryEmit(refreshSignal.value.not())
    }

    private fun findTheme(isUseCustomTheme: Boolean, theme: Theme): Theme {
        return if (isUseCustomTheme) {
            theme
        } else {
            val currentTime = LocalTime.now().second
            Timber.d("Cal ${currentTime}s")
            if ((currentTime / 10) % 2 == 0) {
                Theme.Default
            } else {
                Theme.DarkDefault
            }
        }
    }

    override fun fetchTheme() {
        updateAppTheme(context, currentTheme)
    }

    private fun updateAppTheme(context: Context, theme: Theme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val uiModeManager = context.getSystemService<UiModeManager>()!!
            uiModeManager.setApplicationNightMode(
                when (theme) {
                    Theme.Light -> UiModeManager.MODE_NIGHT_NO
                    Theme.Dark -> UiModeManager.MODE_NIGHT_YES
                }
            )
        } else {
            AppCompatDelegate.setDefaultNightMode(
                when (theme) {
                    Theme.Light -> AppCompatDelegate.MODE_NIGHT_NO
                    Theme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
                }
            )
        }
    }
}
