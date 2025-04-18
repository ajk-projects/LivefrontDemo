package com.example.livefrontdemo.view.stateholder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.data.repository.model.AccountDetailResult
import com.example.livefrontdemo.view.stateholder.model.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val accountRepository: AccountRepository,
    val authRepository: AuthRepository,
) : ViewModel() {
    private val _accountState = MutableStateFlow<AccountState>(AccountState.Unknown)
    val accountState = _accountState.asStateFlow()

    init {
        getAccount()
    }

    fun getAccount() {
        viewModelScope.launch {
            _accountState.value = AccountState.Loading
            _accountState.value = authRepository.getDid()?.let { dId ->
                when (val result = accountRepository.getAccountInfo(handle = dId)) {
                    is AccountDetailResult.Success -> AccountState.Success(accountDetail = result.accountDetail)
                    is AccountDetailResult.Error -> AccountState.Error(message = R.string.user_data_load_error)
                    else -> AccountState.Unknown
                }
            } ?: AccountState.Error(message = R.string.no_user_found_error)
        }
    }
}