package com.example.livefrontdemo.view.compose.posting

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import com.example.livefrontdemo.R
import com.example.livefrontdemo.view.compose.tags.TestTags
import com.example.livefrontdemo.view.stateholder.model.PostState
import org.junit.Rule
import org.junit.Test

class ComposePostBottomSheetViewKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCharCount() {
        composeTestRule.setContent {
            ComposePostBottomSheetView(
                postState = PostState.Unknown,
                dismissBottomSheet = { },
                submitPost = { _ -> }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.Posting.POST_TEXT_FIELD).performClick().performTextInput("123456789")
        composeTestRule.onNodeWithTag(TestTags.Posting.POST_CHAR_COUNT).assertTextEquals("291")
        composeTestRule.onNodeWithTag(TestTags.Posting.POST_TEXT_FIELD).performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.Posting.POST_TEXT_FIELD).performTextInput(THREE_HUNDRED_CHARS + "1")
        composeTestRule.onNodeWithTag(TestTags.Posting.POST_CHAR_COUNT).assertTextEquals("-1")
    }

    @Test
    fun testErrorState() {
        composeTestRule.setContent {
            ComposePostBottomSheetView(
                postState = PostState.Error(message = R.string.posting_error),
                dismissBottomSheet = {},
                submitPost = { _ -> },
            )
        }

        composeTestRule.onNodeWithTag(TestTags.Posting.POST_ERROR).assertTextEquals("Failed to post, retry.")
        composeTestRule.onNodeWithTag(TestTags.Posting.POST_BUTTON).assertIsNotEnabled()
    }

    private companion object {
        private const val THREE_HUNDRED_CHARS =
            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec."
    }
}