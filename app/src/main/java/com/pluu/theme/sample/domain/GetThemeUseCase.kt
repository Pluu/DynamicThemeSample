package com.pluu.theme.sample.domain

import com.pluu.theme.sample.data.PreferenceStorage
import com.pluu.theme.sample.di.IoDispatcher
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.model.themeFromStorageKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Theme>(dispatcher) {
    override suspend fun execute(parameters: Unit): Theme {
        // TODO use as flow
        val selectedTheme = preferenceStorage.selectedTheme.first()
        return themeFromStorageKey(selectedTheme)
            ?: Theme.DEFAULT
    }
}