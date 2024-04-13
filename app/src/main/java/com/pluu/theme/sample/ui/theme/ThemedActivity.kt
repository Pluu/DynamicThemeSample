package com.pluu.theme.sample.ui.theme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.utils.ThemeHelper
import com.pluu.theme.sample.utils.ThemeProvider
import kotlinx.coroutines.launch

abstract class ThemedActivity : AppCompatActivity(), ThemeProvider {

    private val themedViewModel by viewModels<ThemedViewModel>()

    @StyleRes
    override var themeId: Int = 0

    protected val currentTheme: Theme
        get() = themedViewModel.currentTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeHelper.setTheme(this, themedViewModel.currentTheme)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    themedViewModel.theme.collect { theme ->
                        ThemeHelper.setCorrectTheme(this@ThemedActivity, theme)
                    }
                }
            }
        }
    }

    override fun setTheme(resId: Int) {
        super.setTheme(resId)
        this.themeId = resId
    }
}