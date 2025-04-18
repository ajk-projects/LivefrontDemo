package com.example.livefrontdemo.data.repository

import com.example.livefrontdemo.data.repository.model.AccountDetailResult

interface AccountRepository {
    /**
     * Get the account information for a given user. Pulls from the network or in-memory cache.
     *
     * @param handle The user's handle.
     * @param refresh Pull from the network & refresh the cache. Defaults to false.
     * @return An [AccountDetailResult] indicating success with data or failure.
     */
    suspend fun getAccountInfo(handle: String, refresh: Boolean = false): AccountDetailResult
}