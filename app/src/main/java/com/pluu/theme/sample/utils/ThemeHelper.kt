package com.pluu.theme.sample.utils

import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import com.pluu.theme.sample.R
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.model.Theme.Blue
import com.pluu.theme.sample.model.Theme.BlueFromDark
import com.pluu.theme.sample.model.Theme.DEFAULT
import com.pluu.theme.sample.model.Theme.Purple
import com.pluu.theme.sample.model.Theme.PurpleFromDark

object ThemeHelper {

    fun setTheme(activity: AppCompatActivity, theme: Theme) {
        setTheme(activity, getTheme(theme))
    }

    private fun setTheme(activity: AppCompatActivity, @StyleRes resId: Int) {
        activity.setTheme(resId)
    }

    fun saveTheme(activity: AppCompatActivity) {
        activity.recreate()
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

    fun setCorrectTheme(activity: AppCompatActivity, newTheme: Theme) {
        val currentTheme = (activity as ThemeProvider).themeId
        val newThemeId = getTheme(newTheme)
        setTheme(activity, newThemeId)
        if (currentTheme != newThemeId) {
            activity.recreate()
        }
    }
}

interface ThemeProvider {
    val themeId: Int
}