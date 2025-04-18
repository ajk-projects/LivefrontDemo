package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.fakes.FakeAccountRepository
import com.example.livefrontdemo.fakes.FakeAuthRepository
import com.example.livefrontdemo.view.stateholder.model.AccountState
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccountViewModelTest {
    private lateinit var sut: AccountViewModel

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

    private fun initSubject(
        accountRepository: AccountRepository = FakeAccountRepository(),
        authRepository: AuthRepository = FakeAuthRepository(),
    ) {
        sut = AccountViewModel(
            accountRepository = accountRepository,
            authRepository = authRepository,
        )
    }

    @Test
    fun `test unauthenticated user returns error`() = testScope.runTest {
        initSubject()

        sut.getAccount()
        advanceUntilIdle()

        assertTrue(sut.accountState.value is AccountState.Error)
    }

    @Test
    fun `test authenticated user returns success`() = testScope.runTest {
        initSubject(
            authRepository = FakeAuthRepository(authenticated = true)
        )

        sut.getAccount()
        advanceUntilIdle()

        assertTrue(sut.accountState.value is AccountState.Success)
    }

    @Test
    fun `test account repo exception returns error`() = testScope.runTest {
        initSubject(
            authRepository = FakeAuthRepository(authenticated = true),
            accountRepository = FakeAccountRepository(throwError = true)
        )

        sut.getAccount()
        advanceUntilIdle()

        assertTrue(sut.accountState.value is AccountState.Error)
    }
}