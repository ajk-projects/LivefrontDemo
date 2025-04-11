package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.AuthRepository
import com.example.livefrontdemo.fakes.FakeAccountRepository
import com.example.livefrontdemo.fakes.FakeAuthRepository
import com.example.livefrontdemo.view.stateholder.model.AccountState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class AccountViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val coroutineExtension = UnconfinedTestDispatcher()

    private lateinit var sut: AccountViewModel

    private fun initSubject(
        accountRepository: AccountRepository = FakeAccountRepository(),
        authRepository: AuthRepository = FakeAuthRepository(),
    ) {
        sut = AccountViewModel(
            accountRepository = accountRepository,
            authRepository = authRepository,
            ioDispatcher = coroutineExtension,
        )
    }

    @Test
    fun `test unauthenticated user returns error`() = runTest {
        initSubject()

        sut.getAccount()

        assertTrue(sut.accountState.value is AccountState.Error)
    }

    @Test
    fun `test authenticated user returns success`() = runTest {
        initSubject(
            authRepository = FakeAuthRepository(authenticated = true)
        )

        sut.getAccount()

        assertTrue(sut.accountState.value is AccountState.Success)
    }

    @Test
    fun `test account repo exception returns error`() = runTest {
        initSubject(
            authRepository = FakeAuthRepository(authenticated = true),
            accountRepository = FakeAccountRepository(throwError = true)
        )

        sut.getAccount()

        assertTrue(sut.accountState.value is AccountState.Error)
    }
}