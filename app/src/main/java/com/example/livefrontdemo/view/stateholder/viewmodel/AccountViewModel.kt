package com.example.livefrontdemo.view.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.view.stateholder.model.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val accountRepository: AccountRepository,
    val authRepository: AuthRepository,
    val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _accountState = MutableStateFlow<AccountState>(AccountState.Unknown)
    val accountState = _accountState.asStateFlow()

    init {
        getAccount()
    }

    fun getAccount() {
        viewModelScope.launch(ioDispatcher) {
            _accountState.value = AccountState.Loading
            authRepository.getDid()?.let { dId ->
                runCatching {
                    val result = accountRepository.getAccountInfo(handle = dId)
                    _accountState.value = AccountState.Success(accountDetail = result)
                }.onFailure { throwable ->
                    _accountState.value = AccountState.Error(message = R.string.no_user_found_error)
                }.getOrElse {
                    _accountState.value = AccountState.Error(message = R.string.no_user_found_error)
                }
            } ?: run {
                _accountState.value = AccountState.Error(message = R.string.no_user_found_error)
            }
        }
    }
}