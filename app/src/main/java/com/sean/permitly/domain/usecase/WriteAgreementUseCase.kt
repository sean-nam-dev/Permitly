package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys.AGREEMENT
import com.sean.permitly.domain.repository.AppSettingsRepository

class WriteAgreementUseCase(
    private val appSettingsRepository: AppSettingsRepository
) {
    suspend operator fun invoke(value: Boolean) = appSettingsRepository.insert(
        key = AGREEMENT,
        value = value
    )
}