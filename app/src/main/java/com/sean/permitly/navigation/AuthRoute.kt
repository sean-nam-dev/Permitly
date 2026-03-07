package com.sean.permitly.navigation

import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph

sealed class AuthRoute {

    @Serializable
    data object Onboarding : AuthRoute()

    @Serializable
    data object Login : AuthRoute()
}