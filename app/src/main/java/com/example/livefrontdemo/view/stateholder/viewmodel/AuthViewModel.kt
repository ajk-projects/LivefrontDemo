package com.example.livefrontdemo.view.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.datastore.AuthDataStore
import com.example.livefrontdemo.data.datastore.model.StartAuthResult
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.view.stateholder.LogoutStateHolder
import com.example.livefrontdemo.view.stateholder.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataStore: AuthDataStore,
    private val logoutStateHolder: LogoutStateHolder,
) : ViewModel() {
    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Unknown)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        _authState.value = if (authRepository.isAuthenticated()) {
            AuthState.Authenticated
        } else {
            AuthState.UnAuthenticated()
        }
        viewModelScope.launch {
            logoutStateHolder.logoutTrigger.collect {
                signOut()
            }
        }
    }

    /**
     * Init an auth session for Blue Sky using username & password.
     * Ignore refresh token for the purposes of this demo app.
     */
    fun signIn(username: String, password: String) {
        _authState.value = AuthState.ProcessingAuthChange
        viewModelScope.launch {
            _authState.value = when (val result = authDataStore.startAuthSession(username, password)) {
                is StartAuthResult.Success -> {
                    authRepository.updateAuthSession(
                        dId = result.dId,
                        accessToken = result.accessToken,
                    )
                    AuthState.Authenticated
                }

                is StartAuthResult.Failure -> AuthState.UnAuthenticated(withError = R.string.login_error)
            }
        }
    }

    /**
     * Clear credentials and update state.
     */
    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _authState.value = AuthState.UnAuthenticated()
        }
    }
}