package com.example.livefrontdemo.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.livefrontdemo.wiring.Encrypted
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @Encrypted private val encryptedSharedPreferences: SharedPreferences,
) : AuthRepository {
    private val updateAuthMutex = Mutex()

    override suspend fun updateAuthSession(
        dId: String?,
        accessToken: String?,
    ) {
        updateAuthMutex.withLock {
            encryptedSharedPreferences
                .edit {
                    putString(BSKY_HANDLE, dId)
                    putString(BSKY_TOKEN, accessToken)
                }
        }
    }

    override suspend fun signOut() {
        updateAuthMutex.withLock {
            encryptedSharedPreferences
                .edit {
                    remove(BSKY_HANDLE)
                    remove(BSKY_TOKEN)
                }
        }
    }

    override fun isAuthenticated(): Boolean =
        encryptedSharedPreferences.getString(BSKY_TOKEN, null) != null

    override fun getDid(): String? = encryptedSharedPreferences.getString(BSKY_HANDLE, null)

    override fun getAccessToken(): String? = encryptedSharedPreferences.getString(BSKY_TOKEN, null)

    companion object {
        private const val BSKY_HANDLE = "BSKY_HANDLE"
        private const val BSKY_TOKEN = "BSKY_TOKEN"
    }
}