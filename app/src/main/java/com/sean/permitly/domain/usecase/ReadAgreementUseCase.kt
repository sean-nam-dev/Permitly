package com.sean.permitly.domain.usecase

import com.sean.permitly.core.Key
import com.sean.permitly.domain.repository.AppPreferencesRepository

class ReadAgreementUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    operator fun invoke() = appPreferencesRepository.read(
        key = Key.AGREEMENT.name,
        default = false
    )
}