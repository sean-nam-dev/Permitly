package com.sean.permitly.domain.usecase

import com.sean.permitly.core.Key
import com.sean.permitly.domain.repository.AppPreferencesRepository

class ReadFirstLaunchUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    operator fun invoke() = appPreferencesRepository.read(
        key = Key.FIRST_LAUNCH.name,
        default = false
    )
}