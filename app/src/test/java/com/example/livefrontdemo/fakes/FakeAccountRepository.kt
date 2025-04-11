package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.model.AccountDetail
import com.example.livefrontdemo.data.repository.model.exception.AccountException

class FakeAccountRepository(
    var throwError: Boolean = false,
) : AccountRepository {

    override suspend fun getAccountInfo(
        handle: String,
        refresh: Boolean,
    ): AccountDetail {
        if (throwError) throw AccountException()
        return AccountDetail(
            handle = "ajkueterman.com",
            displayName = "AJ Kueterman",
            avatarUrl = "",
            bannerUrl = "",
            description = "A basic description for tests.",
            followersCount = 100,
            followingCount = 150,
        )
    }
}