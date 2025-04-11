package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.fakes.FakeFeedRepository
import com.example.livefrontdemo.view.stateholder.model.FeedState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val coroutineExtension = UnconfinedTestDispatcher()

    private lateinit var sut: FeedViewModel

    private fun initSubject(
        feedRepository: FeedRepository = FakeFeedRepository(),
    ) {
        sut = FeedViewModel(
            feedRepository = feedRepository,
            ioDispatcher = coroutineExtension,
        )
    }

    @Test
    fun `test success result`() = runTest {
        initSubject()

        sut.getFeed()

        assertTrue(sut.feedState.value is FeedState.Success)
    }

    @Test
    fun `test error result`() = runTest {
        initSubject(
            feedRepository = FakeFeedRepository(throwError = true)
        )

        sut.getFeed()

        assertTrue(sut.feedState.value is FeedState.Error)
    }

    @Test
    fun `test refresh is passed through to repo and returns success`() = runTest {
        initSubject()

        sut.getFeed()

        assertEquals(1, (sut.feedState.value as? FeedState.Success)?.posts?.size)

        sut.getFeed(forceRefresh = true)

        assertEquals(2, (sut.feedState.value as? FeedState.Success)?.posts?.size)
    }
}