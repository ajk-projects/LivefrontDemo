package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.datastore.model.StartAuthResult

interface AuthDataStore {
    /**
     * Initiate an authentication session, returning an access & refresh JWT token.
     *
     * @param username The user's email address.
     * @param password The user's password.
     * @return A result class encapsulating a success (with tokens), or failure.
     */
    suspend fun startAuthSession(username: String, password: String): StartAuthResult
}