package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.repository.model.AccountDetail
import com.example.livefrontdemo.data.repository.model.exception.AccountException

interface AccountRepository {
    /**
     * Get the account information for a given user. Pulls from the network or in-memory cache.
     *
     * @param handle The user's handle.
     * @param refresh Pull from the network & refresh the cache. Defaults to false.
     * @return The account information.
     * @throws AccountException If the account cannot be retrieved.
     */
    @Throws(AccountException::class)
    suspend fun getAccountInfo(handle: String, refresh: Boolean = false): AccountDetail
}