package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.datastore.model.GetAuthorResult

interface AuthorDataStore {
    /**
     * Get profile information for a given user.
     *
     * @param dIdOrHandle The user's dId or handle.
     * @return A result class encapsulating a success (with profile information), or error.
     */
    suspend fun getAuthorDetails(dIdOrHandle: String): GetAuthorResult
}