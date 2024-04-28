package com.pluu.theme.sample.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.pluu.theme.sample.di.ThemedActivityDelegateInterface
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
abstract class ThemedActivity : AppCompatActivity() {

    private lateinit var themedActivityDelegate: ThemedActivityDelegate

    protected val currentTheme: Theme
        get() = themedActivityDelegate.currentTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        themedActivityDelegate = EntryPointAccessors.fromApplication(
            this,
            ThemedActivityDelegateInterface::class.java
        ).getDelegate()
        initializeTheme()

        super.onCreate(savedInstanceState)
    }

    private fun initializeTheme() {
        val defaultNightMode = getNightMode()
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)
    }

    private fun getNightMode(): Int {
        val defaultNightMode = if (themedActivityDelegate.currentTheme.isLight) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        return defaultNightMode
    }
}