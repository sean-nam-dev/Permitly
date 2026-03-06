package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.domain.repository.AppSettingsRepository
import javax.inject.Inject

class ReadFirstLaunchUseCase @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) {
    operator fun invoke() = appSettingsRepository.read(
        key = AppSettingsKeys.FIRST_LAUNCH,
        default = true
    )
}