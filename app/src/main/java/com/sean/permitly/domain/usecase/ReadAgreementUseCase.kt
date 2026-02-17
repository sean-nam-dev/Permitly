package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.PreferencesKeys
import com.sean.permitly.domain.repository.AppPreferencesRepository

class ReadAgreementUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    operator fun invoke() = appPreferencesRepository.read(
        key = PreferencesKeys.AGREEMENT,
        default = false
    )
}