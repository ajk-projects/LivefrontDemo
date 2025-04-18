package com.example.livefrontdemo.fakes

import com.example.livefrontdemo.data.getTimelinePosts
import com.example.livefrontdemo.data.repository.FeedRepository
import com.example.livefrontdemo.data.repository.model.TimelineResult

class FakeFeedRepository(private val throwError: Boolean = false) : FeedRepository {
    override suspend fun getMyTimeline(refresh: Boolean): TimelineResult {
        if (throwError) return TimelineResult.Error()
        return if (refresh) {
            TimelineResult.Success(
                posts = getTimelinePosts(count = 2)
            )
        } else {
            TimelineResult.Success(
                posts = getTimelinePosts()
            )
        }
    }
}