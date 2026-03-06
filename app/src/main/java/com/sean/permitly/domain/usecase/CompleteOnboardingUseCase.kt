package com.sean.permitly.domain.usecase

import com.sean.permitly.domain.error.flatMap
import com.sean.permitly.domain.model.State
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val writeStateUseCase: WriteStateUseCase,
    private val writeAgreementUseCase: WriteAgreementUseCase
) {
    suspend operator fun invoke(
        state: State,
        isAgreementAccepted: Boolean
    ) = writeStateUseCase(state).flatMap {
        writeAgreementUseCase(isAgreementAccepted)
    }
}