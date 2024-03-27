package com.pluu.theme.sample.initializers

import android.content.Context
import androidx.startup.Initializer
import com.pluu.theme.sample.MainApplication
import com.pluu.theme.sample.utils.ComponentLogger

class LifecycleInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val application = context as MainApplication
        ComponentLogger().initialize(application)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        TimberInitializer::class.java
    )
}
