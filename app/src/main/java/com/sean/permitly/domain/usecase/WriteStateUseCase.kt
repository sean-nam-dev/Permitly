package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.PreferencesKeys
import com.sean.permitly.domain.repository.AppPreferencesRepository
import com.sean.permitly.presentation.onboarding.util.State

class WriteStateUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    suspend operator fun invoke(state: State) = appPreferencesRepository.insert(
        key = PreferencesKeys.STATE,
        value = state.name
    )
}