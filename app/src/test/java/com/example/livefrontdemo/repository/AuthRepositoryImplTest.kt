package com.example.livefrontdemo.repository

import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.data.repository.AuthRepositoryImpl
import com.example.livefrontdemo.fakes.InMemorySharedPreferences
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AuthRepositoryImplTest {
    private lateinit var sut: AuthRepository

    private fun initSubject() {
        sut = AuthRepositoryImpl(
            encryptedSharedPreferences = InMemorySharedPreferences()
        )
    }

    @Test
    fun `test auth session init`() = runTest {
        initSubject()

        assert(!sut.isAuthenticated())
        assert(sut.getDid() == null)
        assert(sut.getAccessToken() == null)

        sut.updateAuthSession(dId = "test", accessToken = "test")

        assert(sut.isAuthenticated())
        assert(sut.getDid() == "test")
        assert(sut.getAccessToken() == "test")
    }

    @Test
    fun `test auth session update`() = runTest {
        initSubject()

        sut.updateAuthSession(dId = "test", accessToken = "test")

        assert(sut.isAuthenticated())
        assert(sut.getDid() == "test")
        assert(sut.getAccessToken() == "test")

        sut.updateAuthSession(dId = "test change", accessToken = "test change")

        assert(sut.isAuthenticated())
        assert(sut.getDid() == "test change")
        assert(sut.getAccessToken() == "test change")
    }

    @Test
    fun `test auth session sign out`() = runTest {
        initSubject()
        sut.updateAuthSession(dId = "test", accessToken = "test")
        sut.signOut()

        assert(!sut.isAuthenticated())
        assert(sut.getDid() == null)
        assert(sut.getAccessToken() == null)
    }
}