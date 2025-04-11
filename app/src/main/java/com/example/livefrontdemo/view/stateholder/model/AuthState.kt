package com.example.livefrontdemo.view.stateholder.model

import androidx.annotation.StringRes

sealed class AuthState {
    data object Unknown : AuthState()
    data class UnAuthenticated(@StringRes val withError: Int? = null) : AuthState()
    data object ProcessingAuthChange : AuthState()
    data object Authenticated : AuthState()
}