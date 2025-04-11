package com.example.livefrontdemo.view.stateholder.viewmodel

import com.example.livefrontdemo.R
import com.example.livefrontdemo.data.datastore.PublishPostDataStore
import com.example.livefrontdemo.fakes.FakePublishPostDataStore
import com.example.livefrontdemo.view.stateholder.model.PostState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PublishPostViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val coroutineExtension = UnconfinedTestDispatcher()

    private lateinit var sut: PublishPostViewModel

    private fun initSubject(
        publishPostDataStore: PublishPostDataStore = FakePublishPostDataStore(),
    ) {
        sut = PublishPostViewModel(
            publishPostDataStore = publishPostDataStore,
            ioDispatcher = coroutineExtension,
        )
    }

    @Test
    fun `test publish post`() = runTest {
        initSubject()

        sut.publishPost("test")

        assertTrue(sut.postState.value is PostState.Success)
    }

    @Test
    fun `test publish failure`() = runTest {
        initSubject(
            publishPostDataStore = FakePublishPostDataStore(returnError = true)
        )

        sut.publishPost("test")

        assertEquals(PostState.Error(message = R.string.posting_error), sut.postState.value)
    }

    @Test
    fun `test display bottom sheet`() = runTest {
        initSubject()

        sut.displayComposeView()

        assertTrue(sut.displayComposeBottomSheet.value)
    }

    @Test
    fun `test dismiss bottom sheet`() = runTest {
        initSubject()

        sut.displayComposeView()

        assertTrue(sut.displayComposeBottomSheet.value)

        sut.dismissComposeView()

        assertFalse(sut.displayComposeBottomSheet.value)
    }

    @Test
    fun `test success dismisses bottom sheet`() {
        initSubject()
        sut.displayComposeView()

        sut.publishPost("test")

        assertFalse(sut.displayComposeBottomSheet.value)
    }

    @Test
    fun `test failure dismisses bottom sheet`() {
        initSubject(
            publishPostDataStore = FakePublishPostDataStore(returnError = true)
        )
        sut.displayComposeView()

        sut.publishPost("test")

        assertTrue(sut.displayComposeBottomSheet.value)
    }
}