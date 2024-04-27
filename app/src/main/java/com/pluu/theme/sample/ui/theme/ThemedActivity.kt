package com.pluu.theme.sample.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ThemedActivity : AppCompatActivity() {

    @Inject
    lateinit var themedActivityDelegate: ThemedActivityDelegate

    protected val currentTheme: Theme
        get() = themedActivityDelegate.currentTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeTheme()
    }

    private fun initializeTheme() {
        val defaultNightMode = when (themedActivityDelegate.currentTheme) {
            Theme.Light -> AppCompatDelegate.MODE_NIGHT_NO
            Theme.Dark -> AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)
        delegate.applyDayNight()
    }
}