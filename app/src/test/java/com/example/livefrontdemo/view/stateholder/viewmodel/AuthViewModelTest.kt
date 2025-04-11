package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.datastore.AuthDataStore
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.fakes.FakeAuthDataStore
import com.example.livefrontdemo.fakes.FakeAuthRepository
import com.example.livefrontdemo.view.stateholder.LogoutStateHolder
import com.example.livefrontdemo.view.stateholder.model.AuthState
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val coroutineExtension = UnconfinedTestDispatcher()

    private val logoutStateHolder: LogoutStateHolder = mockk {
        coEvery { logoutTrigger.collect(any()) } just awaits
        coEvery { notifyLogout() } returns Unit
    }

    private lateinit var sut: AuthViewModel

    private fun initSubject(
        authRepository: AuthRepository = FakeAuthRepository(),
        authDataStore: AuthDataStore = FakeAuthDataStore(),
    ) {
        sut = AuthViewModel(
            authRepository = authRepository,
            authDataStore = authDataStore,
            logoutStateHolder = logoutStateHolder,
            ioDispatcher = coroutineExtension,
        )
    }

    @Test
    fun `test basic sign in`() = runTest {
        initSubject()

        sut.signIn("aj@kueterman.com", "password")

        assertEquals(AuthState.Authenticated, sut.authState.value)
    }

    @Test
    fun `test failed sign in`() = runTest {
        initSubject(authDataStore = FakeAuthDataStore(returnError = true))

        sut.signIn("aj@kueterman.com", "password")

        assertEquals(AuthState.UnAuthenticated(withError = R.string.login_error), sut.authState.value)
    }

    @Test
    fun `sign out`() = runTest {
        initSubject(authDataStore = FakeAuthDataStore(returnError = true))

        sut.signIn("aj@kueterman.com", "password")

        assertEquals(AuthState.UnAuthenticated(withError = R.string.login_error), sut.authState.value)

        sut.signOut()

        assertEquals(AuthState.UnAuthenticated(), sut.authState.value)
    }

    @Test
    fun `sign out tracker collected`() = runTest {
        initSubject()
        coVerify { logoutStateHolder.logoutTrigger.collect(any()) }
    }
}