package com.example.livefrontdemo.data.repository.model

sealed class AccountDetailResult {
    data object None : AccountDetailResult()

    data class Success(
        val accountDetail: AccountDetail,
    ) : AccountDetailResult()

    data class Error(
        val message: String? = null,
    ) : AccountDetailResult()
}