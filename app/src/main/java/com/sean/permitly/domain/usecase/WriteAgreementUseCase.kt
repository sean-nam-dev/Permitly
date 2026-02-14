package com.sean.permitly.domain.usecase

import com.sean.permitly.core.Key
import com.sean.permitly.domain.repository.AppPreferencesRepository

class WriteAgreementUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    suspend operator fun invoke(value: Boolean) = appPreferencesRepository.insert(
        key = Key.AGREEMENT.name,
        value = value
    )
}