package com.pluu.theme.sample.ui.theme

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pluu.theme.sample.R
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.utils.ThemeHelper
import com.pluu.theme.sample.utils.getColorFromTheme
import kotlinx.coroutines.launch
import timber.log.Timber

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

    override fun onRestart() {
        super.onRestart()
        themedViewModel.refreshTheme()
    }

    private fun updateTheme(newTheme: Theme) {
        val themeResId = ThemeHelper.getTheme(newTheme)
        if (this.themeId != themeResId) {
            Timber.d("Update theme = $newTheme")
            ActivityCompat.recreate(this)
        }
    }

    private fun setTheme(theme: Theme) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Timber.d("Apply theme = $theme")
        val themeResId = ThemeHelper.getTheme(theme)
        this.themeId = themeResId
        setTheme(themeResId)

        // Update statusBar/navigationBar
        val bgColor= getColorFromTheme(R.attr.custom_color_background)
        window.statusBarColor = bgColor
        window.navigationBarColor = bgColor
        with(WindowCompat.getInsetsController(window, window.decorView)) {
            isAppearanceLightStatusBars = ColorUtils.calculateLuminance(bgColor) >= 0.5
        }
    }
}