package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.repository.AuthRepository

class FakeAuthRepository(authenticated: Boolean = false) : AuthRepository {
    private var isAuthenticated = authenticated
    private var did: String? = if (authenticated) "ajkueterman.com" else null
    private var accessToken: String? = if (authenticated) "jwt" else null

    override suspend fun updateAuthSession(dId: String?, accessToken: String?) {
        this.did = dId
        this.accessToken = accessToken
        this.isAuthenticated = true
    }

    override suspend fun signOut() {
        this.did = null
        this.accessToken = null
        this.isAuthenticated = false
    }

    override fun isAuthenticated(): Boolean = isAuthenticated

    override fun getDid(): String? = did

    override fun getAccessToken(): String? = accessToken
}