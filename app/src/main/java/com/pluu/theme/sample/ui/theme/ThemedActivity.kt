package com.pluu.theme.sample.ui.theme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.utils.ThemeHelper
import kotlinx.coroutines.launch

abstract class ThemedActivity : AppCompatActivity() {

    private val themedViewModel by viewModels<ThemedViewModel>()

    @StyleRes
    private var themeId: Int = 0

    protected val currentTheme: Theme
        get() = themedViewModel.currentTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(themedViewModel.currentTheme)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    themedViewModel.theme.collect { theme ->
                        updateTheme(theme)
                    }
                }
            }
        }
    }

    private fun updateTheme(newTheme: Theme) {
        val newThemeId = ThemeHelper.getTheme(newTheme)
        if (themeId != newThemeId) {
            ActivityCompat.recreate(this)
        }
    }

    private fun setTheme(theme: Theme) {
        val themeResId = ThemeHelper.getTheme(theme)
        setTheme(themeResId)
    }

    override fun setTheme(resId: Int) {
        super.setTheme(resId)
        this.themeId = resId
        theme.applyStyle(resId, true)
    }
}