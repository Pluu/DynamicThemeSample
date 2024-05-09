package com.pluu.theme.sample.ui.base

import com.pluu.theme.sample.domain.GetThemeUseCase
import com.pluu.theme.sample.domain.GetUseCustomThemeUseCase
import com.pluu.theme.sample.domain.result.successOr
import com.pluu.theme.sample.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

interface ThemedActivityDelegate {
    val currentTheme: Theme
    val isUsedCustomTheme: Boolean
//    fun init()
//    fun refresh()
}

@Singleton
class ThemedActivityDelegateImpl @Inject constructor(
//    @ApplicationScope private val externalScope: CoroutineScope,
//    private val observeUseCustomThemeModeUseCase: ObserveUseCustomThemeModeUseCase,
    private val isUseCustomThemeUseCase: GetUseCustomThemeUseCase,
//    private val observeThemeUseCase: ObserveThemeModeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
) : ThemedActivityDelegate {

    private val refreshSignal = MutableStateFlow(false)

//    override fun init() {
//        combine(
//            refreshSignal,
//            observeUseCustomThemeModeUseCase(Unit),
//            observeThemeUseCase(Unit)
//        ) { _, isUsedCustomTheme, currentTheme ->
//            findTheme(isUsedCustomTheme, currentTheme)
//        }.distinctUntilChanged()
//            .onEach {
//                updateAppTheme(it)
//            }.launchIn(externalScope + Dispatchers.Main.immediate)
//    }

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

//    override fun refresh() {
//        refreshSignal.tryEmit(refreshSignal.value.not())
//    }

//    private fun findTheme(isUseCustomTheme: Result<Boolean>, theme: Result<Theme>): Theme {
//        return findTheme(isUseCustomTheme.successOr(false), theme.successOr(Theme.Default))
//    }

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

//    private fun updateAppTheme(theme: Theme) {
//        val defaultNightMode = when (theme) {
//            Theme.Light -> AppCompatDelegate.MODE_NIGHT_NO
//            Theme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
//        }
//        AppCompatDelegate.setDefaultNightMode(defaultNightMode)
//    }
}