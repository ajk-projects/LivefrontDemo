package com.example.livefrontdemo.view.stateholder

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Shared state holder object to notify Auth ViewModel of sign out events from the Interceptor.
 */
@Singleton
class LogoutStateHolder @Inject constructor() {
    private val _logoutTrigger = MutableSharedFlow<Unit>(replay = 0)
    val logoutTrigger = _logoutTrigger.asSharedFlow()

    suspend fun notifyLogout() {
        _logoutTrigger.emit(Unit)
    }
}