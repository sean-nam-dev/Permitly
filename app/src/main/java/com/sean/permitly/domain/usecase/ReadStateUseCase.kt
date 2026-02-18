package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.domain.model.State
import com.sean.permitly.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.map

class ReadStateUseCase(
    private val appSettingsRepository: AppSettingsRepository
) {
    operator fun invoke() = appSettingsRepository.read(
        key = AppSettingsKeys.STATE,
        default = State.NONE.name
    ).map {
        try {
            State.valueOf(it)
        } catch (_: Exception) {
            State.NONE
        }
    }
}