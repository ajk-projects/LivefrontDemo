package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.datastore.PublishPostDataStore
import com.example.livefrontdemo.fakes.FakePublishPostDataStore
import com.example.livefrontdemo.view.stateholder.model.PostState
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PublishPostViewModelTest {
    private lateinit var sut: PublishPostViewModel

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
        publishPostDataStore: PublishPostDataStore = FakePublishPostDataStore(),
    ) {
        sut = PublishPostViewModel(
            publishPostDataStore = publishPostDataStore,
        )
    }

    @Test
    fun `test publish post`() = testScope.runTest {
        initSubject()

        sut.publishPost("test")
        advanceUntilIdle()

        assertTrue(sut.postState.value is PostState.Success)
    }

    @Test
    fun `test publish failure`() = testScope.runTest {
        initSubject(
            publishPostDataStore = FakePublishPostDataStore(returnError = true)
        )

        sut.publishPost("test")
        advanceUntilIdle()

        assertEquals(PostState.Error(message = R.string.posting_error), sut.postState.value)
    }

    @Test
    fun `test display bottom sheet`() = testScope.runTest {
        initSubject()

        sut.displayComposeView()
        advanceUntilIdle()

        assertTrue(sut.displayComposeBottomSheet.value)
    }

    @Test
    fun `test dismiss bottom sheet`() = testScope.runTest {
        initSubject()

        sut.displayComposeView()
        advanceUntilIdle()

        assertTrue(sut.displayComposeBottomSheet.value)

        sut.dismissComposeView()
        advanceUntilIdle()

        assertFalse(sut.displayComposeBottomSheet.value)
    }

    @Test
    fun `test success dismisses bottom sheet`() = testScope.runTest {
        initSubject()
        sut.displayComposeView()
        advanceUntilIdle()

        sut.publishPost("test")
        advanceUntilIdle()

        assertFalse(sut.displayComposeBottomSheet.value)
    }

    @Test
    fun `test failure dismisses bottom sheet`() = testScope.runTest {
        initSubject(
            publishPostDataStore = FakePublishPostDataStore(returnError = true)
        )
        sut.displayComposeView()
        advanceUntilIdle()

        sut.publishPost("test")
        advanceUntilIdle()

        assertTrue(sut.displayComposeBottomSheet.value)
    }
}