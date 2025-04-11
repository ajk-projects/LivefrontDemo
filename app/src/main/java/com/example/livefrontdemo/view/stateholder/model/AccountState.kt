package com.example.livefrontdemo.view.stateholder.model

import androidx.annotation.StringRes
import com.example.livefrontdemo.data.repository.model.AccountDetail

sealed class AccountState {
    data object Unknown : AccountState()
    data object Loading : AccountState()
    data class Success(val accountDetail: AccountDetail) : AccountState()
    data class Error(@StringRes val message: Int) : AccountState()
}