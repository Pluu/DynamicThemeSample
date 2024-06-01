package com.pluu.theme.sample.ui.base

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.pluu.theme.sample.di.ThemedActivityDelegateInterface
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
abstract class ThemedActivity : AppBaseActivity() {

    private lateinit var themedActivityDelegate: ThemedActivityDelegate

    override fun isForceLightTheme(): Boolean = false

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            initDelegate(newBase)
        }
        super.attachBaseContext(newBase)
    }

    private fun initDelegate(newBase: Context) {
        themedActivityDelegate = EntryPointAccessors.fromApplication(
            newBase,
            ThemedActivityDelegateInterface::class.java
        ).getDelegate()
        initializeTheme()
    }

    private fun initializeTheme() {
        AppCompatDelegate.setDefaultNightMode(
            if (themedActivityDelegate.currentTheme.isLight) {
                AppCompatDelegate.MODE_NIGHT_NO
            } else {
                AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }

    override fun onStart() {
        super.onStart()
        initializeTheme()
    }
}