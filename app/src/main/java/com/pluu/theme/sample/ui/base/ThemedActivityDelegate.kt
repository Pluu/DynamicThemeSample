package com.pluu.theme.sample.ui.base

import com.pluu.theme.sample.domain.GetThemeUseCase
import com.pluu.theme.sample.domain.GetUseCustomThemeUseCase
import com.pluu.theme.sample.domain.result.successOr
import com.pluu.theme.sample.model.Theme
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

interface ThemedActivityDelegate {
    val currentTheme: Theme
    val isUsedCustomTheme: Boolean
}

@Singleton
class ThemedActivityDelegateImpl @Inject constructor(
    private val isUseCustomThemeUseCase: GetUseCustomThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
) : ThemedActivityDelegate {

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
}
