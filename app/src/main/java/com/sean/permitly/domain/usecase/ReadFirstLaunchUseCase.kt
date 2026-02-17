package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.PreferencesKeys
import com.sean.permitly.domain.repository.AppPreferencesRepository

class ReadFirstLaunchUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    operator fun invoke() = appPreferencesRepository.read(
        key = PreferencesKeys.FIRST_LAUNCH,
        default = false
    )
}