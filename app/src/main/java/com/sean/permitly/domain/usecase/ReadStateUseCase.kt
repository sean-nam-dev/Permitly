package com.sean.permitly.domain.usecase

import com.sean.permitly.core.Key
import com.sean.permitly.domain.repository.AppPreferencesRepository
import com.sean.permitly.core.State

class ReadStateUseCase(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    operator fun invoke() = appPreferencesRepository.read(
        key = Key.STATE.name,
        default = State.NJ.name
    )
}