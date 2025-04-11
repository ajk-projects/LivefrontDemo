package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.datastore.AuthDataStore
import com.example.livefrontdemo.data.datastore.model.StartAuthResult

class FakeAuthDataStore(val returnError: Boolean = false) : AuthDataStore {
    override suspend fun startAuthSession(
        username: String,
        password: String,
    ): StartAuthResult {
        return if (returnError) {
            StartAuthResult.Failure("error")
        } else StartAuthResult.Success(
            dId = username,
            accessToken = "accessJwt",
            refreshToken = "refreshJwt",
        )
    }
}