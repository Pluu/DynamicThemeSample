package com.pluu.theme.sample

import android.app.Application
import com.pluu.theme.sample.ui.theme.ThemedActivityDelegate
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var themedActivityDelegate: ThemedActivityDelegate

    override fun onCreate() {
        super.onCreate()
        themedActivityDelegate.init()
    }
}