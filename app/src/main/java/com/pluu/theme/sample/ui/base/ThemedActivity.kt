package com.pluu.theme.sample.ui.base

import android.os.Bundle
import com.pluu.theme.sample.di.ThemedActivityDelegateInterface
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors

@AndroidEntryPoint
abstract class ThemedActivity : AppBaseActivity() {

    private lateinit var themedActivityDelegate: ThemedActivityDelegate

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
        themedActivityDelegate.fetchTheme()
    }

    override fun onResume() {
        super.onResume()
        updateThemeTranslation = true
        initializeTheme()
    }
}