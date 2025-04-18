package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.model.AccountDetail
import com.example.livefrontdemo.data.repository.model.AccountDetailResult

class FakeAccountRepository(
    var throwError: Boolean = false,
) : AccountRepository {

    override suspend fun getAccountInfo(
        handle: String,
        refresh: Boolean,
    ): AccountDetailResult {
        if (throwError) return AccountDetailResult.Error()
        return AccountDetailResult.Success(
            accountDetail = AccountDetail(
                handle = "ajkueterman.com",
                displayName = "AJ Kueterman",
                avatarUrl = "",
                bannerUrl = "",
                description = "A basic description for tests.",
                followersCount = 100,
                followingCount = 150,
            )
        )
    }
}