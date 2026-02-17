package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.PreferencesKeys
import com.sean.permitly.domain.repository.AppPreferencesRepository

class WriteFirstLaunchUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    suspend operator fun invoke(value: Boolean) = appPreferencesRepository.insert(
        key = PreferencesKeys.FIRST_LAUNCH,
        value = value
    )
}