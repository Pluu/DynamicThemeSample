package com.pluu.theme.sample.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.pluu.theme.sample.di.ThemedActivityDelegateInterface
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
abstract class ThemedActivity : AppBaseActivity() {

    private lateinit var themedActivityDelegate: ThemedActivityDelegate

    protected val currentTheme: Theme
        get() = themedActivityDelegate.currentTheme

    override fun isForceLightTheme(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
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
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)
    }

    override fun onResume() {
        initializeTheme()
        super.onResume()
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