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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private lateinit var sut: AuthViewModel

    val testScheduler = TestCoroutineScheduler()
    val testDispatcher = StandardTestDispatcher(testScheduler)
    val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val logoutStateHolder: LogoutStateHolder = mockk {
        coEvery { logoutTrigger.collect(any()) } just awaits
        coEvery { notifyLogout() } returns Unit
    }

    private fun initSubject(
        authRepository: AuthRepository = FakeAuthRepository(),
        authDataStore: AuthDataStore = FakeAuthDataStore(),
    ) {
        sut = AuthViewModel(
            authRepository = authRepository,
            authDataStore = authDataStore,
            logoutStateHolder = logoutStateHolder,
        )
    }

    @Test
    fun `test basic sign in`() = testScope.runTest {
        initSubject()

        sut.signIn("aj@kueterman.com", "password")
        advanceUntilIdle()

        assertEquals(AuthState.Authenticated, sut.authState.value)
    }

    @Test
    fun `test failed sign in`() = testScope.runTest {
        initSubject(authDataStore = FakeAuthDataStore(returnError = true))

        sut.signIn("aj@kueterman.com", "password")
        advanceUntilIdle()

        assertEquals(AuthState.UnAuthenticated(withError = R.string.login_error), sut.authState.value)
    }

    @Test
    fun `sign out`() = testScope.runTest {
        initSubject(authDataStore = FakeAuthDataStore(returnError = true))

        sut.signIn("aj@kueterman.com", "password")
        advanceUntilIdle()

        assertEquals(AuthState.UnAuthenticated(withError = R.string.login_error), sut.authState.value)

        sut.signOut()
        advanceUntilIdle()

        assertEquals(AuthState.UnAuthenticated(), sut.authState.value)
    }

    @Test
    fun `sign out tracker collected`() = testScope.runTest {
        initSubject()
        advanceUntilIdle()
        coVerify { logoutStateHolder.logoutTrigger.collect(any()) }
    }
}