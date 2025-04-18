package com.example.livefrontdemo.repository

import com.example.livefrontdemo.data.datastore.FeedDataStore
import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.data.repository.FeedRepositoryImpl
import com.example.livefrontdemo.data.repository.model.TimelineResult
import com.example.livefrontdemo.fakes.FakeFeedDataStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedRepositoryImplTest {
    private lateinit var sut: FeedRepository

    private fun initSubject(
        feedDataStore: FeedDataStore = FakeFeedDataStore(),
    ) {
        sut = FeedRepositoryImpl(
            feedDataStore = feedDataStore,
        )
    }

    @Test
    fun `fetch feed posts`() = runTest {
        initSubject()

        val result = sut.getMyTimeline() as? TimelineResult.Success

        assert(result?.posts.isNullOrEmpty().not())
    }

    @Test
    fun `fetch feed posts with error`() = runTest {
        initSubject(feedDataStore = FakeFeedDataStore(returnError = true))

        assertTrue(sut.getMyTimeline() is TimelineResult.Error)
    }

    @Test
    fun `fetch feed posts uses cache`() = runTest {
        val mockDataStore: FeedDataStore = mockk()
        coEvery { mockDataStore.getTimeline() } returns FakeFeedDataStore().getTimeline()
        initSubject(
            feedDataStore = mockDataStore
        )

        sut.getMyTimeline()
        sut.getMyTimeline()

        coVerify(exactly = 1) { mockDataStore.getTimeline() }
    }

    @Test
    fun `fetch feed posts refresh busts cache`() = runTest {
        val mockDataStore: FeedDataStore = mockk()
        coEvery { mockDataStore.getTimeline() } returns FakeFeedDataStore().getTimeline()
        initSubject(
            feedDataStore = mockDataStore
        )

        sut.getMyTimeline()
        sut.getMyTimeline(refresh = true)

        coVerify(exactly = 2) { mockDataStore.getTimeline() }
    }
}