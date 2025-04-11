package com.example.livefrontdemo.view.compose.tags

sealed class TestTags {
    data object Login : TestTags() {
        const val LOGIN_ERROR = "login_error_message"
    }

    data object Posting : TestTags() {
        const val POST_TEXT_FIELD = "post_text_field"
        const val POST_CHAR_COUNT = "post_char_count"
        const val POST_ERROR = "post_error_message"
        const val POST_BUTTON = "post_button"
    }
}