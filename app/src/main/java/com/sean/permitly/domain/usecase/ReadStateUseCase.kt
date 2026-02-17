package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.domain.repository.AppSettingsRepository
import com.sean.permitly.presentation.onboarding.util.State

class ReadStateUseCase(
    private val appSettingsRepository: AppSettingsRepository
) {
    operator fun invoke() = appSettingsRepository.read(
        key = AppSettingsKeys.STATE,
        default = State.NJ.name
    )
}