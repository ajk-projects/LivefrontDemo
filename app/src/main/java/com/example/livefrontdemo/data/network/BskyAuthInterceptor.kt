package com.example.livefrontdemo.data.network

import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.view.stateholder.LogoutStateHolder
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BskyAuthInterceptor @Inject constructor(
    val authRepo: AuthRepository,
    val logoutStateHolder: LogoutStateHolder,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var response: Response? = null

        return try {
            response = authRepo.getAccessToken()?.let { accessToken ->
                val requestWithHeader = originalRequest.newBuilder()
                    .header(
                        AUTH_HEADER_NAME,
                        AUTH_HEADER_VALUE_PREFIX + authRepo.getAccessToken()
                    )
                    .build()
                chain.proceed(requestWithHeader)
            } ?: chain.proceed(originalRequest)

            // Check for expired tokens (they are 400's, not 401's from the Bluesky API)
            val peek = response.peekBody(1024).string()
            if (peek.contains("ExpiredToken")) {
                runBlocking {
                    authRepo.signOut()
                    logoutStateHolder.notifyLogout()
                }
            }

            response
        } catch (_: Exception) {
            response ?: chain.proceed(originalRequest)
        }
    }

    private companion object {
        private const val AUTH_HEADER_NAME = "Authorization"
        private const val AUTH_HEADER_VALUE_PREFIX = "Bearer "
    }
}