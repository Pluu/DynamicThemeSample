package com.pluu.theme.sample.domain

import com.pluu.theme.sample.data.PreferenceStorage
import com.pluu.theme.sample.di.DefaultDispatcher
import com.pluu.theme.sample.domain.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

open class ObserveUseCustomThemeModeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Boolean>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<Boolean>> {
        return preferenceStorage.usedCustomTheme.map {
            Result.Success(it)
        }
    }
}
