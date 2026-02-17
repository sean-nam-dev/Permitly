package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.PreferencesKeys
import com.sean.permitly.domain.repository.AppPreferencesRepository
import com.sean.permitly.presentation.onboarding.util.State

class ReadStateUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    operator fun invoke() = appPreferencesRepository.read(
        key = PreferencesKeys.STATE,
        default = State.NJ.name
    )
}