package com.pluu.theme.sample.domain

import com.pluu.theme.sample.data.PreferenceStorage
import com.pluu.theme.sample.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class SetUseCustomThemeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Boolean, Unit>(dispatcher) {
    override suspend fun execute(parameters: Boolean) {
        preferenceStorage.useCustomTheme(parameters)
    }
}
