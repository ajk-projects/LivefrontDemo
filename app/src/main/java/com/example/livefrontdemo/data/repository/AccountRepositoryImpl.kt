package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.datastore.AuthorDataStore
import com.example.livefrontdemo.data.datastore.model.GetAuthorResult
import com.example.livefrontdemo.data.repository.model.AccountDetail
import com.example.livefrontdemo.data.repository.model.AccountDetailResult
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val authorDataStoreImpl: AuthorDataStore,
) : AccountRepository {
    private val accountMutex = Mutex()

    private var accountDetailResult: AccountDetailResult = AccountDetailResult.None

    override suspend fun getAccountInfo(dIdOrHandle: String, refresh: Boolean): AccountDetailResult {
        val currentResultAsSuccess = accountDetailResult as? AccountDetailResult.Success
        if (refresh || currentResultAsSuccess?.accountDetail?.handle.isNullOrEmpty() || currentResultAsSuccess.accountDetail.handle != dIdOrHandle) {
            val result = authorDataStoreImpl.getAuthorDetails(dIdOrHandle = dIdOrHandle)
            accountMutex.withLock {
                accountDetailResult = when (result) {
                    is GetAuthorResult.Success ->
                        AccountDetailResult.Success(
                            accountDetail = AccountDetail(
                                handle = result.author.handle,
                                displayName = result.author.displayName,
                                avatarUrl = result.author.avatar,
                                bannerUrl = result.author.banner,
                                description = result.author.description,
                                followersCount = result.author.followersCount,
                                followingCount = result.author.followsCount,
                            )
                        )

                    is GetAuthorResult.Error -> AccountDetailResult.Error(message = result.message)
                }
            }
        }
        return accountMutex.withLock { this.accountDetailResult }
    }
}