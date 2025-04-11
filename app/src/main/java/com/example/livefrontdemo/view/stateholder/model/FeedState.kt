package com.example.livefrontdemo.view.stateholder.model

import androidx.annotation.StringRes
import com.example.livefrontdemo.data.repository.model.TimelinePost

sealed class FeedState {
    data object Unknown : FeedState()
    data object Loading : FeedState()
    data class Success(val posts: List<TimelinePost>) : FeedState()
    data class Error(@StringRes val message: Int) : FeedState()
}