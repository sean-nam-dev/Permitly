package com.sean.permitly.presentation.onboarding.pages.agreement

data class AgreementPageData(
    val isAgreementAccepted: Boolean,
    val onAgreementClick: () -> Unit
)
