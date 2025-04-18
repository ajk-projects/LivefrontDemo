package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.fakes.FakeFeedRepository
import com.example.livefrontdemo.view.stateholder.model.FeedState
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {
    private lateinit var sut: FeedViewModel

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
        feedRepository: FeedRepository = FakeFeedRepository(),
    ) {
        sut = FeedViewModel(
            feedRepository = feedRepository,
        )
    }

    @Test
    fun `test success result`() = testScope.runTest {
        initSubject()

        sut.getFeed()
        advanceUntilIdle()

        assertTrue(sut.feedState.value is FeedState.Success)
    }

    @Test
    fun `test error result`() = testScope.runTest {
        initSubject(
            feedRepository = FakeFeedRepository(throwError = true)
        )

        sut.getFeed()
        advanceUntilIdle()

        assertTrue(sut.feedState.value is FeedState.Error)
    }

    @Test
    fun `test refresh is passed through to repo and returns success`() = testScope.runTest {
        initSubject()

        sut.getFeed()
        advanceUntilIdle()

        assertEquals(1, (sut.feedState.value as? FeedState.Success)?.posts?.size)

        sut.getFeed(forceRefresh = true)
        advanceUntilIdle()

        assertEquals(2, (sut.feedState.value as? FeedState.Success)?.posts?.size)
    }
}