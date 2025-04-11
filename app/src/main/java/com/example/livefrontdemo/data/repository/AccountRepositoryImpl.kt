package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.datastore.AuthorDataStore
import com.example.livefrontdemo.data.datastore.model.GetAuthorResult
import com.example.livefrontdemo.data.repository.model.AccountDetail
import com.example.livefrontdemo.data.repository.model.exception.AccountException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val authorDataStoreImpl: AuthorDataStore,
) : AccountRepository {
    private val accountMutex = Mutex()

    private var accountDetail = AccountDetail()

    @Throws(AccountException::class)
    override suspend fun getAccountInfo(dIdOrHandle: String, refresh: Boolean): AccountDetail {
        if (refresh || accountDetail.handle.isNullOrEmpty() || accountDetail.handle != dIdOrHandle) {
            val result = authorDataStoreImpl.getAuthorDetails(dIdOrHandle = dIdOrHandle)
            accountMutex.withLock {
                accountDetail = when (result) {
                    is GetAuthorResult.Success -> {
                        AccountDetail(
                            handle = result.author.handle,
                            displayName = result.author.displayName,
                            avatarUrl = result.author.avatar,
                            bannerUrl = result.author.banner,
                            description = result.author.description,
                            followersCount = result.author.followersCount,
                            followingCount = result.author.followsCount,
                        )
                    }

                    is GetAuthorResult.Error -> throw AccountException()
                }
            }
        }
        return accountMutex.withLock { this.accountDetail }
    }
}