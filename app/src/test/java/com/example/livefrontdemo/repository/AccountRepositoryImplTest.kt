package com.example.livefrontdemo.repository

import com.example.livefrontdemo.data.datastore.AuthorDataStore
import com.example.livefrontdemo.data.repository.AccountRepository
import com.example.livefrontdemo.data.repository.AccountRepositoryImpl
import com.example.livefrontdemo.data.repository.model.exception.AccountException
import com.example.livefrontdemo.fakes.FakeAuthorDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test

class AccountRepositoryImplTest {

    private lateinit var sut: AccountRepository

    private fun initSubject(
        authorDataStore: AuthorDataStore = FakeAuthorDataStore(),
    ) {
        sut = AccountRepositoryImpl(
            authorDataStoreImpl = authorDataStore,
        )
    }

    @Test
    fun `test get author detail`() = runTest {
        initSubject()

        val result = sut.getAccountInfo(handle = "test")

        assert(result.handle == "test")
    }

    @Test
    fun `test get author detail fail throws ex`() = runTest {
        initSubject(authorDataStore = FakeAuthorDataStore(returnError = true))

        assertThrows(AccountException::class.java) {
            runBlocking {
                sut.getAccountInfo(handle = "test")
            }
        }
    }

    @Test
    fun `test get author detail uses cache`() = runTest {
        val mockDataStore: AuthorDataStore = mockk()
        coEvery { mockDataStore.getAuthorDetails(any()) } returns FakeAuthorDataStore().getAuthorDetails("test")
        initSubject(
            authorDataStore = mockDataStore
        )

        sut.getAccountInfo(handle = "test")
        sut.getAccountInfo(handle = "test")

        coVerify(exactly = 1) { mockDataStore.getAuthorDetails(any()) }
    }

    @Test
    fun `test get author detail refresh busts cache`() = runTest {
        val mockDataStore: AuthorDataStore = mockk()
        coEvery { mockDataStore.getAuthorDetails(any()) } returns FakeAuthorDataStore().getAuthorDetails("test")
        initSubject(
            authorDataStore = mockDataStore
        )

        sut.getAccountInfo(handle = "test")
        sut.getAccountInfo(handle = "test", refresh = true)

        coVerify(exactly = 2) { mockDataStore.getAuthorDetails(any()) }
    }

    @Test
    fun `test get author detail with new handle busts cache`() = runTest {
        val mockDataStore: AuthorDataStore = mockk()
        coEvery { mockDataStore.getAuthorDetails(any()) } returns FakeAuthorDataStore().getAuthorDetails("test")
        initSubject(
            authorDataStore = mockDataStore
        )

        sut.getAccountInfo(handle = "test")
        sut.getAccountInfo(handle = "test again")

        coVerify(exactly = 2) { mockDataStore.getAuthorDetails(any()) }
    }
}