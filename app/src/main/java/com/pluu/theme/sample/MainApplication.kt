package com.pluu.theme.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
//    @Inject lateinit var themedActivityDelegate: ThemedActivityDelegate

    override fun onCreate() {
        super.onCreate()
//        themedActivityDelegate.currentTheme
    }
}