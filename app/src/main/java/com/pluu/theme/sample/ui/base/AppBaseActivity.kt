package com.pluu.theme.sample.ui.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

abstract class AppBaseActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun attachBaseContext(newBase: Context?) {
        if (isForceLightTheme()) {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        }
        super.attachBaseContext(newBase)
    }

    protected open fun isForceLightTheme(): Boolean = true
}