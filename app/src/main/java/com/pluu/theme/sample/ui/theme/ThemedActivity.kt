package com.pluu.theme.sample.ui.theme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.pluu.theme.sample.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class ThemedActivity : AppCompatActivity() {

    @Inject
    lateinit var themedActivityDelegate: ThemedActivityDelegate

//    @StyleRes
//    private var themeId: Int = 0

//    protected val currentTheme: Theme
//        get() = themedViewModel.currentTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeTheme()
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    themedViewModel.theme.collect { theme ->
//                        updateTheme(theme)
//                    }
//                }
//            }
//        }
    }

    private fun initializeTheme() {
        val defaultNightMode = when (themedActivityDelegate.currentTheme) {
            Theme.Yellow -> AppCompatDelegate.MODE_NIGHT_NO
            Theme.PurpleFromDark -> AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(defaultNightMode)
    }

    protected fun recreateCompat() {
        ActivityCompat.recreate(this)
    }

    private fun updateTheme(newTheme: Theme) {
//        val themeResId = ThemeHelper.getTheme(newTheme)
//        if (this.themeId != themeResId) {
//            Timber.d("Update theme = $newTheme")
//            ActivityCompat.recreate(this)
//        }
    }
}