package com.example.livefrontdemo.data.datastore

import com.example.livefrontdemo.data.api.BlueSkyApi
import com.example.livefrontdemo.data.api.model.AuthRequestApiModel
import com.example.livefrontdemo.data.datastore.model.StartAuthResult
import javax.inject.Inject

class AuthDataStoreImpl @Inject constructor(
    private val bskyApi: BlueSkyApi,
) : AuthDataStore {
    override suspend fun startAuthSession(
        username: String,
        password: String,
    ): StartAuthResult =
        runCatching {
            bskyApi.initAuthSession(
                body = AuthRequestApiModel(
                    identifier = username,
                    password = password
                )
            )
        }.mapCatching { response ->
            val authResponse = response.body()
            if (response.isSuccessful && authResponse != null) {
                StartAuthResult.Success(
                    dId = authResponse.did,
                    accessToken = authResponse.accessJwt,
                    refreshToken = authResponse.refreshJwt,
                )
            } else {
                StartAuthResult.Failure(SERVICE_FAILURE)
            }
        }.onFailure { throwable ->
            StartAuthResult.Failure(reason = throwable.message)
        }.getOrElse {
            StartAuthResult.Failure(reason = UNKNOWN_ERROR)
        }

    private companion object {
        private const val SERVICE_FAILURE = "service_failure"
        private const val UNKNOWN_ERROR = "unknown_error"
    }
}