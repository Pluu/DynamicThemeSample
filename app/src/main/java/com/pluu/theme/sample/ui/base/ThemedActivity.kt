package com.pluu.theme.sample.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.pluu.theme.sample.R
import com.pluu.theme.sample.di.ThemedActivityDelegateInterface
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
abstract class ThemedActivity : AppBaseActivity() {

    private lateinit var themedActivityDelegate: ThemedActivityDelegate

    protected val currentTheme: Theme
        get() = themedActivityDelegate.currentTheme

    private var updateThemeTranslation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        updateThemeTranslation = false
        initDelegate()
        super.onCreate(savedInstanceState)
    }

    private fun initDelegate() {
        themedActivityDelegate = EntryPointAccessors.fromApplication(
            this,
            ThemedActivityDelegateInterface::class.java
        ).getDelegate()
        initializeTheme()
    }

    private fun initializeTheme() {
        val defaultNightMode = getNightMode()
        delegate.localNightMode = defaultNightMode
    }

    override fun onResume() {
        super.onResume()
        updateThemeTranslation = true
        initializeTheme()
    }

    override fun onNightModeChanged(mode: Int) {
        super.onNightModeChanged(mode)
        if (updateThemeTranslation) {
            overridePendingTransition(R.anim.short_fade_in, R.anim.short_fade_out)
        }
    }

    private fun getNightMode(): Int {
        val defaultNightMode = if (currentTheme.isLight) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        return defaultNightMode
    }
}