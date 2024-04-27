package com.pluu.theme.sample.di

import com.pluu.theme.sample.ui.theme.ThemedActivityDelegate
import com.pluu.theme.sample.ui.theme.ThemedActivityDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
@Suppress("UNUSED")
abstract class ThemedActivityDelegateModule {

    @Singleton
    @Binds
    abstract fun provideThemedActivityDelegate(
        impl: ThemedActivityDelegateImpl
    ): ThemedActivityDelegate
}


@EntryPoint
@InstallIn(SingletonComponent::class)
interface ThemedActivityDelegateInterface {
    fun getDelegate(): ThemedActivityDelegate
}