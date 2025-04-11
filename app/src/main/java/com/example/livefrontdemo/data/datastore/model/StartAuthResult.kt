package com.example.livefrontdemo.data.datastore.model

sealed class StartAuthResult() {
    data class Success(
        val dId: String? = null,
        val accessToken: String? = null,
        val refreshToken: String? = null,
    ) : StartAuthResult()

    data class Failure(val reason: String? = null) : StartAuthResult()
}
