package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.domain.repository.AppSettingsRepository
import javax.inject.Inject

class WriteFirstLaunchUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(value: Boolean) = appSettingsRepository.insert(
        key = AppSettingsKeys.FIRST_LAUNCH,
        value = value
    )
}