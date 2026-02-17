package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.domain.repository.AppSettingsRepository
import com.sean.permitly.presentation.onboarding.util.State

class WriteStateUseCase(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(state: State) = appSettingsRepository.insert(
        key = AppSettingsKeys.STATE,
        value = state.name
    )
}