package com.example.livefrontdemo.data.repository

/**
 * API for storing and retrieving auth data.
 */
interface AuthRepository {
    /**
     * Save the user `dId` and `accessToken` to local encrypted storage.
     *
     * @param dId The user's Bluesky handle.
     * @param accessToken The user's access token / JWT.
     */
    suspend fun updateAuthSession(dId: String?, accessToken: String?)

    /**
     * Clear the user's auth data from local storage.
     */
    suspend fun signOut()

    /**
     * Check if the user is authenticated (credentials stored).
     * @return true if user credentials are stored in local storage, false otherwise.
     */
    fun isAuthenticated(): Boolean

    /**
     * Get the user's Bluesky handle / dId used for posting & pulling account data.
     * @return The user's dId, if it exists, otherwise null
     */
    fun getDid(): String?

    /**
     * Get the user's access token / JWT. Used for making authenticated API calls.
     * @return The user's access token, if it exists, otherwise null
     */
    fun getAccessToken(): String?
}