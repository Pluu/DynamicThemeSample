package com.pluu.theme.sample.domain

import com.pluu.theme.sample.data.PreferenceStorage
import com.pluu.theme.sample.di.IoDispatcher
import com.pluu.theme.sample.model.Theme
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class SetThemeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Theme, Unit>(dispatcher) {
    override suspend fun execute(parameters: Theme) {
        preferenceStorage.selectTheme(parameters.storageKey)
    }
}
