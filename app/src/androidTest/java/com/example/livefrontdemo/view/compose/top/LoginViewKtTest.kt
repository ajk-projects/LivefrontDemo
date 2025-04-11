package com.example.livefrontdemo.view.compose.top

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.livefrontdemo.R
import com.example.livefrontdemo.view.compose.tags.TestTags
import org.junit.Rule
import org.junit.Test

class LoginViewKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginViewError() {
        composeTestRule.setContent {
            LoginView(
                initSession = { _, _ -> },
                errorMessage = R.string.login_error,
            )
        }

        composeTestRule.onNodeWithTag(TestTags.Login.LOGIN_ERROR).assertTextEquals("Error logging in, retry.")
    }
}