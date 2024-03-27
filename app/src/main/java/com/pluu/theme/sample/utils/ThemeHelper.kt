package com.pluu.theme.sample.utils

import android.app.Activity
import androidx.annotation.StyleRes
import androidx.core.app.ActivityCompat
import com.pluu.theme.sample.R
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.model.Theme.Blue
import com.pluu.theme.sample.model.Theme.BlueFromDark
import com.pluu.theme.sample.model.Theme.DEFAULT
import com.pluu.theme.sample.model.Theme.Purple
import com.pluu.theme.sample.model.Theme.PurpleFromDark

object ThemeHelper {

    fun setTheme(activity: Activity, theme: Theme) {
        setTheme(activity, getTheme(theme))
    }

    private fun setTheme(activity: Activity, @StyleRes resId: Int) {
        (activity as ThemeProvider).themeId = resId
        activity.theme.applyStyle(resId, true)
    }

    @StyleRes
    fun getTheme(currentTheme: Theme): Int {
        return when (currentTheme) {
            DEFAULT -> R.style.jingburger
            Blue -> R.style.Pluu_Blue
            BlueFromDark -> R.style.Pluu_Blue_Dark
            Purple -> R.style.Pluu_Purple
            PurpleFromDark -> R.style.Pluu_Purple_Dark
        }
    }

    fun setCorrectTheme(activity: Activity, newTheme: Theme) {
        val currentTheme = (activity as ThemeProvider).themeId
        val newThemeId = getTheme(newTheme)
        setTheme(activity, newThemeId)
        if (currentTheme != newThemeId) {
            ActivityCompat.recreate(activity)
        }
    }
}

interface ThemeProvider {
    var themeId: Int
}