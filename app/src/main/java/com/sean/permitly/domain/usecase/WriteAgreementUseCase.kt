package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.PreferencesKeys.AGREEMENT
import com.sean.permitly.domain.repository.AppPreferencesRepository

class WriteAgreementUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    suspend operator fun invoke(value: Boolean) = appPreferencesRepository.insert(
        key = AGREEMENT,
        value = value
    )
}