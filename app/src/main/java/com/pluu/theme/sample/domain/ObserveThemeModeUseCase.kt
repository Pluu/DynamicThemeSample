package com.pluu.theme.sample.domain

import com.pluu.theme.sample.data.PreferenceStorage
import com.pluu.theme.sample.di.DefaultDispatcher
import com.pluu.theme.sample.domain.result.Result
import com.pluu.theme.sample.model.Theme
import com.pluu.theme.sample.model.themeFromStorageKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class ObserveThemeModeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Theme>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<Theme>> {
        return preferenceStorage.selectedTheme.map {
            val theme = themeFromStorageKey(it) ?: Theme.DEFAULT
            Result.Success(theme)
        }
    }
}
