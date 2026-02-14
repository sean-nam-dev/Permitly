package com.sean.permitly.domain.usecase

import com.sean.permitly.core.Key
import com.sean.permitly.domain.repository.AppPreferencesRepository
import com.sean.permitly.core.State

class WriteStateUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    suspend operator fun invoke(state: State) = appPreferencesRepository.insert(
        key = Key.STATE.name,
        value = state.name
    )
}